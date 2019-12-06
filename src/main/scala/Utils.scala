package bon.jo

import java.lang.ProcessBuilder.Redirect

import bon.jo.html.{H, htmldsl}
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.jdk.CollectionConverters._

object Utils {
  lazy val t_usage = "Usage:"
  lazy val t_options = "Options:"
  lazy val t_mCmd = "Management Commands:"
  lazy val t_cmds = "Commands:"
  lazy val t_docker = "docker"
  lazy val noMatch = mutable.ListBuffer[String]()

  trait Configured {
    val conf = ConfigFactory.load();
    val confApp = conf.getConfig("consoleapi");

    def getBasePath: String = confApp.getString("baseUrl")
  }

  case class DockerParse(me: Iterator[String]) extends MarkIterator {

    def mark = {

      createMarkIfExists(t_usage)
      createMarkIfExists(t_options)
      createMarkIfExists(t_mCmd)
      createMarkIfExists(t_cmds)

    }


  }

  def nullToOption[A](a: A)(implicit nullEq: A): Option[A] = {
    if (a != null && a != nullEq) {
      Some(a)
    } else {
      None
    }

  }

  case class ImagesRaw(repo: String, tag: String, id: String, created: String, size: String) extends ToHtml {
    def repoTag: String = repo+"/"+tag

    def htmlObj(implicit currentUri: String): H = {
      val isHead = (repo == "REPOSITORY")
      val bal: String = if (isHead) "th" else "td"
      implicit val d = new htmldsl("tr")
      import d._
      bal.toHtml.text = repo
      bal.toHtml.text = tag
      bal.toHtml.text = id
      bal.toHtml.text = created
      bal.toHtml.text = size
      val tdLinkDelete = bal.toHtml
      val tdLinkDuplicate = bal.toHtml
      if (!isHead) {
        d.link("delete", s"${WebServer.cmdPathString}/image/run?options=rm,${id}&redirectP=${java.net.URLEncoder.encode(currentUri ,"utf-8")}")(tdLinkDelete)
        d.link("pull", s"${WebServer.cmdPathString}/pull/run?options=${id}&redirectP=${java.net.URLEncoder.encode(currentUri ,"utf-8")}")(tdLinkDuplicate)
        d.link("run", s"${WebServer.cmdPathString}/run/run?options=-dt,${id}&redirectP=${java.net.URLEncoder.encode(currentUri ,"utf-8")}")(tdLinkDuplicate)
      } else {
        tdLinkDelete.text = "delete"

        tdLinkDuplicate.text = "duplicate"
      }
      return d.cont
    }
  }

  object ImagesRaw {
    def apply(ss: Seq[String]): ImagesRaw = {
      ImagesRaw(ss(0), ss(1), ss(2), ss(3), ss(4))
    }

  }

  trait ToHtml {
    def htmlObj(implicit currentUri: String): H
  }

  case class ContainerPs(id: String, image: String, cmd: String, created: String, status: String, ports: String, names: String, imagesRaw: Option[ImagesRaw] = None) extends ToHtml {
    def htmlObj(implicit currentUri: String): H = {
      val isHead = (names == "NAMES")
      val bal: String = if (isHead) "th" else "td"
      val d = new htmldsl("tr")
      import d._
      "td".toHtml.text = id

      val td = "td".toHtml

      if(!isHead){
        val d1 = html.`<div`
        val d2 = html.`<div`
        td child d1 child d2
        d1.text = image
        d2.text = imagesRaw  match {
          case Some(value) => value.repoTag
          case None => ""
        }
      }else{
        td.text = image
      }

      bal.toHtml.text = cmd
      bal.toHtml.text = created
      bal.toHtml.text = status
      bal.toHtml.text = ports
      bal.toHtml.text = names
      val tdLinkDelete = bal.toHtml
      val tdLinkDuplicate = bal.toHtml
      if (!isHead) {
        d.link("delete", s"${WebServer.cmdPathString}/rm/run?options=-f,${names}&redirectP=${java.net.URLEncoder.encode(currentUri ,"utf-8")}")(tdLinkDelete)
        d.link("duplicate", s"${WebServer.cmdPathString}/run/run?options=-dt,${image}&redirectP=${java.net.URLEncoder.encode(currentUri ,"utf-8")}")(tdLinkDuplicate)
      } else {
        tdLinkDelete.text = "delete"
        tdLinkDuplicate.text = "duplicate"
      }
      return d.cont
    }
  }

  object ContainerPs {
    def apply(ss: Seq[String]): ContainerPs = {
      ContainerPs(ss(0), ss(1), ss(2), ss(3), ss(4), ss(5), ss(6))
    }


  }

  def readRowPs(str: String)(implicit headStr: String) = {

    getRange.map {
      case (s, Some(end)) => str.substring(s, end)
      case (s, None) => str.substring(s)
    } map (_.trim)
  }

  def getRange(implicit headStr: String) = {
    val lIndiceStart = (contextReaderFactory.read(headStr).filter(_.isStart).map(_._offset))
    lIndiceStart.zipWithIndex.map(e => (e._1, if (e._2 + 1 < lIndiceStart.length) Some(lIndiceStart(e._2 + 1)) else None))
  }

  def parse[T <: ToHtml](str: String)(const: Seq[String] => T) = {
    val headStr :: next = str.split("\n").toList
    (headStr :: next).map(e => const(readRowPs(e)(headStr)))
  }

  def parsePss(str: String): Iterable[ContainerPs] = {
    parse[ContainerPs](str)(e => ContainerPs.apply(e))
  }

  def parseImageLs(str: String): Iterable[ImagesRaw] = {
    parse[ImagesRaw](str)(e => ImagesRaw.apply(e))
  }

  case class contextReader(current: Char, next: Option[Char], previous: Option[Char])(implicit str: String, offset: Int = 0) {
    val _offset = offset

    def isStart = previous.isEmpty || {
      this match {
        case contextReader(' ', Some(v), Some(' ')) if v != ' ' => previousContext match {
          case Some(b) => b == contextReaderFactory.BLANK
          case _ => false
        }
        case _ => {
          false
        }
      }
    }

    def previousContext(implicit str: String, offset: Int) = contextReaderFactory(str)(offset - 1)

    def isBlank = this == contextReaderFactory.BLANK
  }

  object contextReaderFactory {
    def BLANK(implicit str: String) = contextReader(' ', Some(' '), Some(' '))

    def apply(str: String)(implicit offset: Int): Option[contextReader] = if (str.nonEmpty) {
      val len = str.length()
      implicit val s = str
      Some(contextReader(
        str(offset), if (offset + 1 < len) Some(str(offset + 1)) else None, if (offset - 1 >= 0) Some(str(offset - 1)) else None
      ))

    } else {
      None
    }

    def read(implicit str: String): IndexedSeq[contextReader] = {
      val r = 0 until str.length()
      for (i <- r) yield {
        implicit val ii: Int = i
        val c = contextReaderFactory(str)
        c match {
          case Some(value) => value
          case _ => throw new IllegalStateException(s"cant create reader on ${str}")
        }
      }
    }
  }

}
object M extends App{
  val p = new ProcessBuilder()
  p.command(List("docker", "run", "--detach","" , "--publish", "80:80", "--volume", "\"./:/var/www/html/\"", "44d3fb29ad3b").asJava)
  p.redirectError(Redirect.INHERIT)
  p.start().waitFor()

}