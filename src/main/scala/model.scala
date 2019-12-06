package bon.jo

import java.lang.ProcessBuilder.Redirect

import bon.jo.Utils._
import bon.jo.commandapi.{CommandMemo, CommandService}
import bon.jo.html._

import scala.collection.mutable
import scala.io.{Codec, Source}
import scala.jdk.CollectionConverters._

package object model {
  val tripleCote = "\"" * 3
  val utf8: Codec = Codec("utf-8")

  object Command {

    def constructorArgs(command: Command) = s""""${command.cmd}","${command.desc}""""

    def toScala(command: Command): String = {

      s"""object ${objectScalaName(command)}  extends Command(${constructorArgs(command)})${corp(command.options, command.usageDesc, command.usage)}""".stripMargin
    }

    @inline
    def objectScalaName(implicit command: Command) = s"`d_${command.cmd}`"

    def corp(options: Option[List[CommandOption]], usageDesc: Option[List[UsageDesc]], usage: Option[String]): String = {

      val op = if (options.nonEmpty) {
        s"""${options.get.map(CommandOption.toScala).map(e => s"   ${e}").mkString("\n")}
  override val options =  Some(List(${options.get.map(CommandOption.scalaAlias).mkString(",")}))"""
      } else ""

      val usageS = if (usage.nonEmpty) {
        s"override val usage =  Some(${tripleCote}${usage.get}${tripleCote})"
      } else ""

      val usageDescS = if (usageDesc.nonEmpty) {
        s"""${usageDesc.get.map(UsageDesc.toScala).map(e => s"   ${e}").mkString("\n")}
   override val usageDesc =  Some(List(${usageDesc.get.map(UsageDesc.scalaAlias).mkString(",")}))"""
      } else ""

      s"""{
                                    $op
                                    $usageS
                                    $usageDescS}"""


    }


    def htmlFile(cds: List[Command]) = {
      implicit val dsl = new htmlPagedsl()

      cds.foreach(e => {
        val a = html"a"
        a.atr = (Map(("href" -> s"./command/${e.cmd}.html")))
        a.text = e.cmd

        {
          implicit val dsl = new htmlPagedsl()

          val p = html"p"
          p.text = e.cmd
          val p2 = html"p"
          p2.text = e.desc

          dsl.cont.toHTMLFile(s"""./command/${e.cmd}.html""")
        }


      })

      dsl.cont.toHTMLFile("index.html")
    }


  }

  object CommandOption {
    val reg = """(-[\w][\w-]*)?,?\s?(--[\w\d-]*)?\s?([\w-]*)?""".r

    def toScala(command: CommandOption) = s"""object ${scalaAlias(command)} extends CommandOption("${command.optionString}",""\"${command.desc}""\")"""

    def scalaAlias(command: CommandOption) = s"`${command.parsed.long.get.replaceFirst("--", "")}`"

    def toHtml(cds: Iterable[CommandOption]): Iterable[H] = {
      cds.map(_.ToHtmlFormFrag())
    }

    def formHtml(cds: Iterable[CommandOption])(parent: Command): H = {
      implicit val dsl = new htmldsl("form")

      dsl.cont.action_=(s"${WebServer.runPathString.replace("$cmdName", parent.cmd)}")
      dsl.cont.method_=("POST")
      dsl.cont.content = toHtml(cds)
      if (parent.usageDesc.get.find(_.isImage).nonEmpty) {
        html"input".list_=("images_data").name_=("images")
        val data = html"datalist".id_=("images_data")
        CommandService.getImage.foreach { e =>
          implicit val cont = data
          add"option".value_=(e.id).text_=(e.repo+"/"+e.tag)
        }
      }

      if (parent.usageDesc.get.find(_.isContainer).nonEmpty) {
        html"input".list_=("containers_data").name_=("containers")
        val data = html"datalist".id_=("containers_data")
        CommandService.getContainer.foreach { e =>
          implicit val cont = data
          add"option".value_=(e.id).text_=(e.names)
        }
      }
      if (parent.usageDesc.get.find(_.isName).nonEmpty) {
        html"label"._for("name").text = ("NAME")
        html"input"._type("text").name_=("name")
      }

      val bt = html"input"
      bt._type("submit")
      bt.text = "run"
      dsl.cont
    }
  }

  case class ParsedOption(short: Option[String], long: Option[String], argType: Option[String])

  abstract case class ValuedCommandOption[V](opt: CommandOption, v: V) {
    def isDefined: Boolean
  }

  class StringOption(opt: CommandOption, v: String) extends ValuedCommandOption[String](opt, v) {
    println(s"a-- ${v}")

    def isDefined: Boolean = (opt.parsed.argType == None || v != null && v.trim.nonEmpty)
  }
  case class CommandArg(arg :String)
  object CommandArg{
    val empty = CommandArg("")
  }
  case class CommandOption(optionString: String, desc: String) {

    val options = optionString.split(",").map(_.trim)

    override def toString() = "Options : " + options.toList + " " + desc

    implicit val neq = ""

    val parsed = optionString match {

      case CommandOption.reg(a, b, c) => ParsedOption(nullToOption(a), nullToOption(b), nullToOption(c))
      case s: String => print(s"no match for : ${s}"); noMatch += optionString; ParsedOption(None, None, None)
    }

    def ToHtmlFormFrag() = {
      implicit val d = new htmldsl("div")


      val label = html"label"
      label.atr = Map("for" -> parsed.long.get)
      label.text = parsed.long.get

      completeInputType(d)


      d.ctx.root
    }

    def completeInputType(implicit d: htmldsl): Unit = {


      val input = html"input"
      parsed match {
        case ParsedOption(_, _, Some(typee)) => {

          input.atr = Map("name" -> parsed.long.get, "type" -> "text")
          val span = html"span"
          span.text = typee
        }
        case ParsedOption(_, _, None) => {
          input.atr = Map("name" -> parsed.long.get, "type" -> "checkbox")
        }
      }
    }

  }

  case class InErr(out: StringBuilder = new StringBuilder, err: StringBuilder = new StringBuilder)

  object InErr {
    def throwException(ret: Int)(implicit out: InErr) = {
      throw new IllegalStateException(s"return code of images is ${ret} with error : ${out.err} ")
    }
  }

  case class Command(
                      cmd: String,
                      desc: String,
                      options: Option[List[CommandOption]] = None,
                      usageDesc: Option[List[UsageDesc]] = None,
                      usage: Option[String] = None,
                    ) {
    def haveImage: Boolean = usageDesc.get.map(_.isImage).reduce(_ || _)
    def haveContainer: Boolean = usageDesc.get.map(_.isContainer).reduce(_ || _)
    def haveName: Boolean = usageDesc.get.map(_.isName).reduce(_ || _)
    val pb = new ProcessBuilder();

    def run: Int = {

      pb.redirectOutput(Redirect.INHERIT)
      pb.redirectError(Redirect.INHERIT)
      pb.command("docker", cmd)
      val p = pb.start()
      p.waitFor()
    }

    val base = List[String]("docker", cmd)

    def run( o: InErr): Int = {


      pb.command("docker", cmd)
      val p = pb.start()

      Source.fromInputStream(p.getInputStream()).foldLeft(o.out)((out, c) => {
        o.out.append(c);
        out
      })
      Source.fromInputStream(p.getErrorStream()).foldLeft(o.err)((err, c) => {
        o.err.append(c);
        err
      })
      p.waitFor()
    }

    def run(options: Any,args : CommandArg = CommandArg.empty)(implicit o: InErr ,memo : Option[CommandMemo] ): Int = {
      val optes = (options match {
        case e: Iterable[_] => {
          e.map(_.asInstanceOf[ValuedCommandOption[_]]).map {
            op => {
              if (op.isDefined) {
                println("op.isDefined" + List(op.opt.parsed.long.get, op.v.toString))
                List(op.opt.parsed.long.get, op.v.toString)
              } else {
                println("op.is not Defined" + List(op.opt.parsed.long.get, op.v.toString))
                Nil
              }
            }
          }
        }
        case _ => ???
      }).flatten
     val cmd = (base ++ optes) :+ args.arg.toString
      println(cmd)
      memo match {
        case Some(value) => value.save(cmd.mkString(" "),cmd)
        case None =>
      }
      pb.command(cmd.asJava)
      val p = pb.start()

      val readAndClose = toBuilder(p)
      readAndClose()
      p.waitFor()


    }

    def run(options: Any with Product)(implicit o: InErr,memo : Option[CommandMemo] ): Int = {
      val l: List[String] = options match {
        case e: CommandOption => List(e.parsed.long.get)
        case _ => (for {
          e <- options.productIterator
        } yield {
          e match {
            case e: CommandOption => e.parsed.long.get
            case _ => ""
          }
        }).toList
      }
      println(base ++ l)
      pb.command((base ++ l).asJava)
      val p = pb.start()

      val readAndClose = toBuilder(p)
      readAndClose()
      p.waitFor()


    }

    def toBuilder(p: Process)(implicit o: InErr,memo : Option[CommandMemo]): () => Unit = {
      val inSource = Source.fromInputStream(p.getInputStream())(utf8)
      val errSource = Source.fromInputStream(p.getErrorStream())(utf8)

      def toBuider(): Unit = {
        inSource.getLines().map(e => e + "\n").foreach(o.out.append(_))
        errSource.getLines().map(e => e + "\n").foreach(o.err.append(_))
        inSource.close()
        errSource.close()
      }

      toBuider _
    }

    def run(options: Option[String])(implicit o: InErr,memo : Option[CommandMemo] ): Int = {

      options match {
        case Some(value) => {

          pb.command((List[String]("docker", cmd) ++ value.split(",").toList).asJava)
        }
        case None => pb.command("docker", cmd)
      }

      val p = pb.start()
      val readAndClose = toBuilder(p)
      val ret = p.waitFor()
      readAndClose()
      ret
    }

    def runHelp = {

      pb.redirectOutput(Redirect.INHERIT)
      pb.redirectError(Redirect.INHERIT)
      pb.command("docker", cmd, "--help")
      val p = pb.start()
      p.waitFor()
    }

    def helpIteratorString: DockerParse = {

      pb.command("docker", cmd, "--help")

      val p = pb.start()
      val s = Source.fromInputStream(p.getInputStream())

      val lb = mutable.ListBuffer[String]()
      lb ++= s.getLines()
      val m = new DockerParse(lb.iterator)
      p.waitFor()
      s.close
      m
    }
  }

  val usageReg = """docker [a-z]* (.*)""".r

  def desc(usage: String): List[UsageDesc] = {
    val ret = mutable.ListBuffer[UsageDesc]()

    val toBeClean = usage match {
      case usageReg(a) => a.split(" ").map(UsageDesc(_)).toList
      case _ => println("no"); Nil
    }

    var nextIsForLast = false
    toBeClean.foldLeft(ret)((c, e) => {
      if (e == UsageDesc("|")) {
        nextIsForLast = true
      } else {
        if (nextIsForLast) {
          nextIsForLast = false
          c(c.size - 1) = UsageDesc(c.last + "|" + e.str)
        } else {
          c += UsageDesc(e.str)
        }
      }
      c
    })
    ret.toList
  }

  case class UsageDesc(str: String) {
    def isOptional: Boolean = str.startsWith("[")

    def isContainer: Boolean = str.contains("CONTAINER")

    def isImage: Boolean = str.contains("IMAGE")
    def isName: Boolean = str.contains("NAME")

    def cleanString: String = str.replaceFirst("\\[", "").reverse.replaceFirst("\\]", "").reverse

  }

  object UsageDesc {
    def toScala(e: UsageDesc): String = s"""object ${scalaAlias(e)} extends UsageDesc(${tripleCote}${e.str}${tripleCote})"""

    def scalaAlias(e: UsageDesc): String = s"""`${e.str}`"""
  }



}

