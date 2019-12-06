package bon.jo.commandapi

import bon.jo.html.{H, htmlPagedsl, htmldsl}
import bon.jo.model.{Command, CommandOption, InErr}
import bon.jo.{Utils, WebServer}
import bon.jo.html._

class TemplateHtml(base: List[Command]) extends View[htmlPagedsl] {

  import WebServer._

  val sideMenu = {
    implicit val dsl = htmldsl()

    val d: H = html"div"
    d.text = "menu"
    base.map(_.cmd).foreach(e => {
      dsl.link(e, s"/${basePath}/${command}/$e/${help}")(d)

    })
    dsl.link("Manange", manageBasePath)(d)
    dsl.ctx.root

  }
  val basePageDsl = new htmlPagedsl()

  def manage(): htmlPagedsl = {
    implicit val d = new htmlPagedsl

    html"div".content_= (sideMenu)
    val dd = html"div"
    d.link("images", imagePath)(dd)
    d.link("containers", containerPath)(dd)
    d.link("memo", memoPath)(dd)
    d
  }

  def imageList(imgs: Iterable[Utils.ImagesRaw]): htmlPagedsl = {
    implicit val dsl = new htmlPagedsl()
    implicit val uri = imagePath
    html"div".content_= (sideMenu)
    val div = html"table"
    div.class_=("table")
    div.content = imgs.map(_.htmlObj)

    dsl
  }

  def commandRun(ret: Int, inErr: InErr): htmlPagedsl = {
    implicit val dsl = new htmlPagedsl()


    html"div".content = {
      List(sideMenu)
    }
    html"h4".text = s"result (${ret}):"
    html"pre".text = inErr.out.toString()
    html"h4".text = s"result (${ret}):"
    html"pre".text = inErr.err.toString()
    dsl
  }

  def containerList(cts: Iterable[Utils.ContainerPs]): htmlPagedsl = {
    implicit val dsl = new htmlPagedsl()
    implicit val uri = containerPath
    html"div".content_= (sideMenu)

    val t =  html"table"
    t.class_=("table")
    t.content = cts.map(_.htmlObj)
    dsl
  }
  def addOptions(c: Command)( implicit  dsl : htmlPagedsl) {
    implicit val row = html"div"
    row.atr = Map("class" -> "row")
    val divOPption: H = add"div"
    divOPption.atr = Map("class" -> "col-sm")
    val ti = add"h3" (divOPption)
    ti.text = "Options"
    c.options match {
      case Some(ops) => {
        ops.foreach(op => {
          add"div" (divOPption).text = op.optionString
          add"div" (divOPption).text = op.desc
        })
        val runCmdForm = add"div" (row)
        runCmdForm.atr = Map("class" -> "col-sm")
        runCmdForm.content_= (CommandOption.formHtml(ops)(c))
      }
      case _ =>
    }
  }
  def commandHelp(c: Command): htmlPagedsl = {
    implicit val dsl = new htmlPagedsl()

    html"div".content_= (sideMenu)
    implicit val linkTitle = dsl.link(s"/${basePath}/$command/${c.cmd}/$run")(dsl.cont)
    val title = add"h1"
    title.text = c.cmd

    html"p".text = c.desc
    html"h2".text = "Usage : "
    html"p".text = c.usage.get


    addOptions(c)


    dsl
  }

}
