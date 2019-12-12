package bon.jo

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directive0, Directive1, HttpApp, Route}
import bon.jo.Utils.Configured
import bon.jo.commandapi.{CommandMemoElement, CommandMemoElements, CommandService, FileCommandMemo, InMemmoryCommandMemo, JsonOut, TemplateHtml, View}
import bon.jo.html._
import bon.jo.model._

import scala.collection.immutable
import scala.concurrent.Future
import scala.util.Success
import scala.concurrent.duration._

object WebServer extends HttpApp with App with Configured with JsonOut with CORSHandler {

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

  val memo = new FileCommandMemo()
  implicit val m = Some(memo)

  //val base = DockerCommands.all.values.toList
  val templates: View[htmlPagedsl] = new TemplateHtml(base)

  //def sideMenu = templates.sideMenu
  def routeListCmdRunAndInfo: List[Route] =
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
                  } else if (c.havePath) {
                    CommandArg(params("path"))
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


  def manageRoute: Route = pathPrefix(basePath / manage) {
    concat(corsHandler(routeMemo), routeContniarPs, routeImageLs, {

      done(templates.manage())
    })
  }

  def routeImageLs: Route = {
    path(image) {
      done(templates.imageList(CommandService.getImage))
    }

  }

  def htmlMemo(f: ((String, List[String])) => Boolean): Route = {
    implicit val dsl = new htmlPagedsl
    memo.allAsTextLink(f).foreach { e =>
      val d = html"div"

      dsl.link(e._1, e._2)(d)

    }
    done(dsl)
  }

  def addId(c: (String, List[String])): (Int, String, List[String]) = {
    (c._2.hashCode, c._1, c._2)
  }

  def routeMemo: Route = {
    path(memoString) {
      parameters('format.?, 'name.?, 'newName.?) { (format, name, newName) => {
        var f = name match {
          case Some(value) => (e: (String, List[String])) => e._1 == value
          case None => (_: (String, List[String])) => true
        }
        var mem = memo.allMemo.filter(f).toList
        newName match {
          case Some(n) => if (name.isDefined) {
            val m = memo.saveMemo(n, mem.head._2)
            val oldF = f
            f = (e: (String, List[String])) => oldF(e) || e._1 == n
            mem = m :: mem
          }
          case None =>
        }
        format match {
          case Some(fo) => if (fo == "json") {
            complete(CommandMemoElements(mem.map(addId).map(CommandMemoElement.tupled)))
          } else {
            htmlMemo(f)
          }
          case None => {
            htmlMemo(f)
          }
        }
      }
      }


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


  def routeListCommand: Route = path(basePath / command) {
    val dsl = new htmlPagedsl()

    implicit val rt = dsl.cont

    base.map(_.cmd).foreach(e => {
      dsl.link(e, s"/${getBasePath}/command/" + e + "/help")
    })

    done(dsl)
  }

  def stopRoute = path("stop") {
    implicit val ex = systemReference.get().dispatcher
    Future {
      memo.close()
      binding() match {
        case Success(value) => value.terminate(hardDeadline = 3.seconds).map(e => System.exit(0))
      }
    }
    complete("ok")
  }


  val routeList: List[Route] = stopRoute :: reouteTemplateScala :: manageRoute :: routeListCommand :: routeListCmdRunAndInfo

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


trait CORSHandler {

  import akka.http.scaladsl.model.HttpMethods._
  import akka.http.scaladsl.model.headers._
  import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
  import akka.http.scaladsl.server.Directives._
  import akka.http.scaladsl.server.directives.RouteDirectives.complete
  import akka.http.scaladsl.server.{Directive0, Route}

  import scala.concurrent.duration._

  private val corsResponseHeaders = List(
    `Access-Control-Allow-Origin`.*,
    `Access-Control-Allow-Credentials`(true),
    `Access-Control-Allow-Headers`("Authorization",
      "Content-Type", "X-Requested-With")
  )

  //this directive adds access control headers to normal responses
  private def addAccessControlHeaders: Directive0 = {
    respondWithHeaders(corsResponseHeaders)
  }

  //this handles preflight OPTIONS requests.
  private def preflightRequestHandler: Route = options {
    complete(HttpResponse(StatusCodes.OK).
      withHeaders(`Access-Control-Allow-Methods`(OPTIONS, POST, PUT, GET, DELETE)))
  }

  // Wrap the Route with this method to enable adding of CORS headers
  def corsHandler(r: Route): Route = addAccessControlHeaders {
    preflightRequestHandler ~ r
  }

  // Helper method to add CORS headers to HttpResponse
  // preventing duplication of CORS headers across code
  def addCORSHeaders(response: HttpResponse): HttpResponse =
    response.withHeaders(corsResponseHeaders)
}
