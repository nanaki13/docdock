package bon.jo

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{HttpApp, Route}
import bon.jo.Utils.Configured
import bon.jo.commandapi.{CommandService, InMemmoryCommandMemo, TemplateHtml, View}
import bon.jo.html._
import bon.jo.model._

import scala.collection.immutable

object WebServer extends HttpApp with App with Configured {

  val basePath = getBasePath
  val command = "command"
  val manage = "manage"
  val container = "container"
  val image = "image"
  val run = "run"
  val help = "help"
  val memoString = "memo"

  val manageBasePath = s"/${basePath}/${manage}"
  val runPathString: String = s"/${basePath}/${command}/$$cmdName/run"

  val cmdPathString: String = s"/${basePath}/${command}"
  val containerPath = s"$manageBasePath/${container}"
  val imagePath = s"$manageBasePath/${image}"
  val memoPath = s"$manageBasePath/${memoString}"
  val anayser = DockerAnalyse(List("--help"))
  val base: List[Command] = anayser.getCommand

  val memo = InMemmoryCommandMemo()
  implicit val m = Some(memo)

  //val base = DockerCommands.all.values.toList
  val templates: View[htmlPagedsl] = new TemplateHtml(base)
  //def sideMenu = templates.sideMenu
  val routeListCmdRunAndInfo =
    base.map { c =>
      pathPrefix(basePath / command / c.cmd) {
        concat(
          path(help) {
            done(templates.commandHelp(c))
          }
          ,
          path(run) {
            concat(get {
              parameters('options.?, 'redirectP.?) {
                (option, redirectP) => {

                  implicit val inErr = InErr()

                  val ret = c.run(option)
                  if (redirectP.isEmpty) {

                    done(templates.commandRun(ret, inErr))
                  } else {

                    redirect(redirectP.get, akka.http.scaladsl.model.StatusCodes.Found)
                  }

                }
              }
            },
              post {
                formFieldMap { params =>

                  def paramString(param: (String, String)): IterableOnce[ValuedCommandOption[_]] = {
                    c.options match {
                      case Some(value) => {
                        value.find(_.parsed.long.contains(param._1)) match {
                          case Some(v) => {
                            if (v.parsed.argType.isDefined && param._2.nonEmpty) {
                              if (v.parsed.argType.get == "list") {
                                param._2.split(",").map(new StringOption(v, _))
                              } else {
                                Some(new StringOption(v, param._2))
                              }

                            } else {
                              if (v.parsed.argType.isEmpty && param._2.nonEmpty) {
                                Some(new StringOption(v, ""))
                              } else {
                                None
                              }
                            }

                          }
                          case None => None
                        }


                      }
                      case None => None
                    }
                  }

                  val opts: scala.collection.immutable.Iterable[IterableOnce[bon.jo.model.ValuedCommandOption[_]]] = params.map(paramString)
                  val nonEpty: immutable.Iterable[ValuedCommandOption[_]] = opts.flatten
                  val arg = if (c.haveImage) {
                    CommandArg(params("images"))
                  } else if (c.haveContainer) {
                    CommandArg(params("containers"))
                  } else if (c.haveName) {
                    CommandArg(params("name"))
                  } else {
                    CommandArg.empty
                  }
                  implicit val out = InErr()
                  val ret = c.run(nonEpty, arg)
                  done(templates.commandRun(ret, out))

                }

              })
          }
        )
      }
    }


  val manageRoute = pathPrefix(basePath / manage) {
    concat(routeMemo, routeContniarPs, routeImageLs, {

      done(templates.manage())
    })
  }

  def routeImageLs: Route = {
    path(image) {
      done(templates.imageList(CommandService.getImage))
    }

  }

  def routeMemo: Route = {
    path(memoString) {
      implicit val dsl = new htmlPagedsl
      memo.allAsTextLink.foreach { e =>
        val d = html"div"

        dsl.link(e._1, e._2)(d)

      }
      done(dsl)
    }
  }

  def routeContniarPs: Route = {
    path(container) {
      done(templates.containerList(CommandService.getContainer))
    }
  }

  def reouteTemplateScala: Route = {
    path("template") {

      done(templates.commandRun(0, InErr(new StringBuilder(anayser.createScalaCommand))))
    }
  }


  def done(e: htmlPagedsl) = complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, e.template.toHTMLString))

  val routeListCommand = path(basePath / command) {
    val dsl = new htmlPagedsl()

    implicit val rt = dsl.cont

    base.map(_.cmd).foreach(e => {
      dsl.link(e, s"/${getBasePath}/command/" + e + "/help")
    })

    done(dsl)
  }
  val routeList = reouteTemplateScala :: manageRoute :: (routeListCommand :: routeListCmdRunAndInfo)

  override def routes: Route = {
    concat(routeList: _ *)
  }


  override protected def postHttpBinding(binding: Http.ServerBinding): Unit = {
    super.postHttpBinding(binding)
    val sys = systemReference.get()
    sys.log.info(s"Running on [${sys.name}] actor system")
  }

  override protected def postHttpBindingFailure(cause: Throwable): Unit = {
    println(s"The server could not be started due to $cause")
  }

  // Starting the server
  WebServer.startServer("localhost", 8080)


}



