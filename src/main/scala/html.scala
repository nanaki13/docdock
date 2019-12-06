package bon.jo

import java.io.FileWriter
import java.nio.file.Paths

import scala.collection.mutable
import scala.jdk.CollectionConverters._
import bon.jo.html.H.addToChild

package object html {

 def `<br` = HtmlNode("br")(None)
  def`<ul` = HtmlNode("br")(None)
  def `<li` = HtmlNode("br")(None)
  def `<p` = HtmlNode("br")(None)
  def `<div` = HtmlNode("br")(None)
  def `<form` = HtmlNode("br")(None)
  def `<h1` = HtmlNode("br")(None)

  implicit class HtmlHelper(private val sc: StringContext) extends AnyVal {
    def html(args: Any*)(implicit htmldslp: htmldsl): H = {
      val parsed = sc.parts.mkString("\n")
      (htmldslp :+ (htmldslp.cont)) (parsed)
    }

    def add(args: Any*)(implicit cont: H): H = {

      val n = cont.child(sc.parts.mkString("\n"))
      n.meToParent

      cont.content.last
    }
  }

  class htmlPagedsl extends htmldsl() {

    //def apply
    val base = new TemplateBase

    def template = base.template

    override val ctx: HtmlContext = base.htmlContext

    override implicit def cont: H = base.div
  }

  case class htmldsl(root: String = "div") {
    val ctx: HtmlContext = HtmlContext(root)
    implicit val htmldslp = this

    implicit def cont: H = ctx.root

    def :+(cont: H): String => H = ctx.html_<(_: String)(Some(cont))


    implicit class htmlStringHelpers(str: String) {
      implicit def toHtml(implicit cont: H): H = :+(cont)(str)
    }

    def link(text: String, ref: String)(implicit cont: H): H = {
      val lk: H = add"a";

      lk.atr = Map(("href" -> ref))

      lk.text = text
      lk
    }

    def link(ref: String)(implicit cont: H): H = {
      val lk: H = add"a"
      lk.atr = Map(("href" -> ref))
      lk
    }
  }

  class TemplateBase {

    val htmlContext = HtmlContext("root")

    import htmlContext._

    val HtmlEnv = System.getenv().asScala.iterator.map(e => {
      `<p>`(e._1 + " = " + e._2)
    })

    implicit val noParent: Option[H] = None

    val `<meta/>` = HtmlNode(name = "meta", atr = List("name" -> "viewport", "content" -> "width=device-width, initial-scale=1, shrink-to-fit=no"))
    val `<css/>` = HtmlNode(name = "link", atr = List("rel" -> "stylesheet",
      "href" -> "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
      , "integrity" -> "sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
      , "crossorigin" -> "anonymous"
    ))


    implicit val keep = mutable.ListBuffer[H]()


    htmlContext.startHtml.keep
      .`><head`.`/><body`.`><div`.atr(Map("id" -> "root", "class" -> "container")).`/>`


    val htmlObj = keep.head

    val head = htmlObj.findFirst("head").get
    val div = htmlObj.findFirst("div").get
    head + `<meta/>`
    head + `<css/>`

    def template = htmlObj
  }


  case class HtmlContext(rootName: String) {

    val root = html_(rootName)(None)
    var firstRootChild: H = null

    def startHtml: H = {

      firstRootChild = html_("html")(Some(root))
      firstRootChild
    }

    def html_<(name: String)(implicit root: Option[H]): H = {
      val n = HtmlNode(name);
      H.addToChild(root.get, n);
      n
    }

    def html_(name: String)(implicit parent: Option[H]): H = HtmlNode(name)


    def `<p>`(str: String) = {
      val p = html_("p")(None)
      p.text = str
      p
    }

  }

  case class HtmlNode(name: String, var content: Iterable[H] = Nil, var atr: Iterable[(String, String)] = Nil, var next: Option[H] = None)(implicit var parent: Option[H]) extends H

  class HtmlText(override val text: String)(implicit val _parent: Option[H]) extends HtmlNode(text) with H {
    override val name: String = text

    override def toHTMLString: String = text

  }

  object H {
    def addToChild(parent: H, child: H): H = {
      parent.content = parent.content.foldRight(List(child))(List(_) ++ _)
      child
    }
  }

  sealed trait H {


    this: HtmlNode =>




    def name_=(value:String): this.type = {
      atr = atr.toList :+ ("name" -> value)
      this
    }
    def text: String = ""

    def text_=(text: String): this.type = {
      this + new HtmlText(text)
      this
    }
    def  _for(text: String): this.type = {
      atr = atr.toList :+ ("for" -> text)
      this
    }
    def _type(tpe: String): this.type = {
      atr = atr.toList :+ ("type" -> tpe)
      this
    }

    def _action(s: String): this.type = {
      atr = atr.toList :+ ("action" -> s)
      this
    }

    def method_=(s: String): this.type = {
      atr = atr.toList :+ ("method" -> s)
      this
    }

    def list_=(s: String): this.type = {
      atr = atr.toList :+ ("list" -> s)
      this
    }

    def id_=(s: String): this.type = {
      atr = atr.toList :+ ("id" -> s)
      this
    }

    def value_=(s: String): this.type = {
      atr = atr.toList :+ ("value" -> s)
      this
    }

    def class_=(s: String): this.type = {
      atr = atr.toList :+ ("class" -> s)
      this
    }

    def action_=(s: String): this.type = {
      _action(s)
      this
    }

    def findFirst(value: String): Option[H] = {
      val firstLvel = content.find(_.name == value)
      firstLvel match {
        case Some(_) => firstLvel
        case _ => content.flatMap(_.findFirst(value)).headOption
      }
    }

    def findHead(value: "head"): H = findFirst(value).get

    def keep(implicit keep: mutable.ListBuffer[H]): H = {
      keep += this
      this
    }

    //type Ht = this.type

    def name: String

    var content: Iterable[H]

    def content_=(h: H): Unit = {
      content = List(h)
    }

    var atr: Iterable[(String, String)]

    var next: Option[H]

    def nextToMe(name: String): H = {
      nextToMe(HtmlNode(name)(parent))
    }

    def nextToMe(el: H): H = {

      next = Some(el);
      addToChild(parent.get, next.get);
      next.get
    }

    def child(name: String):  H= {
      val child = HtmlNode(name)(Some(this));
      child
    }

    def child(childEl: H): this.type = {
      childEl.parent = Some(this)
      addToChild(this, childEl)
      this
    }

    def meToParent: H = {
      parent.get.content = parent.get.content.foldRight(List[H](this))(List[H](_) ++ _);
      parent.get
    }

    implicit var parent: Option[H]

    def +(el: H): Unit = {
      this.child(el)
    }

    def `/><div`: H = {
      meToParent;
      this.nextToMe("div")
    }

    def `/><body`: H = {
      meToParent;
      this.nextToMe("body")
    }

    def `/><ul`: H = {
      meToParent;
      this.nextToMe("ul")
    }

    def `/><li`: H = {
      meToParent;
      this.nextToMe("li")
    }

    def `/><p`: H = {
      meToParent;
      this.nextToMe("p")
    }

    def `/><h1`: H = {
      meToParent;
      this.nextToMe("h1")
    }

    def `/><head`: H = {
      meToParent;
      this.nextToMe("head")
    }

    def `/><script`: H = {
      meToParent;
      this.nextToMe("script")
    }

    def `/><meta`: H = {
      meToParent;
      this.nextToMe("meta")
    }

    def `/>`: H = meToParent

    def `><div`: H = child("div")

    def `><head`: H = child("head")

    def `><body`: H = child("body")

    def `><ul`: H = child("ul")

    def `><li`: H = child("li")

    def `><p`: H = child("p")

    def `><h1`: H = child("h1")

    def `<script`: H = child("script")

    def `<meta`: H = child("meta")

    def hcodeString = name + " = " + this.hashCode()

    def atr(at: Map[String, String]): H = {
      this.atr = at;
      this
    }

    def toHTMLString: String = {

      s"""<$name${if (atr.isEmpty) "" else " " + atr.map(e => s"""${e._1}="${e._2}"""").mkString(" ")}>${content.map(_.toHTMLString).mkString("\n")}</$name>"""

    }

    def toHTMLFile(f: String): Unit = {
      try {
        val p = Paths.get(f)
        if (p.getParent() != null && !p.getParent().toFile().exists()) {
          p.getParent().toFile().mkdirs()
        }
      } catch {
        case e: Exception => print(f); e.printStackTrace()
      }


      val ff = new FileWriter(f)
      ff append s"""<$name${if (atr.isEmpty) "" else " " + atr.map(e => s"""${e._1}="${e._2}"""").mkString(" ")}>${content.map(_.toHTMLString).mkString("\n")}</$name>"""
      ff.close()

    }
  }


}
