package bon.jo

import java.io.{File, FileWriter}

import bon.jo.Utils._
import bon.jo.model._

import scala.collection.mutable
import scala.io.Source
import scala.jdk.CollectionConverters._


object DockerAnalyse {
  def createFile: Unit = {
    val sr = DockerAnalyse(List("--help")).createScalaCommand
    val f = new FileWriter("DockerCommands.scala")
    f.append(sr)
    f.close()
  }

  def toConsole = {
    val sr = DockerAnalyse(List("--help")).createScalaCommand
    print(sr)

  }
}


case class DockerAnalyse(dockerCmd: Iterable[String]) {


  val pl = new ProcessBuilder();
  val cmds: Iterable[String] = List(t_docker) ++ dockerCmd
  pl.command(cmds.toList.asJava)

  val p = pl.start()
  val s = Source.fromInputStream(p.getInputStream())
  val lb = mutable.ListBuffer[String]()
  lb ++= s.getLines()

  p.waitFor()
  s.close()
  val m = DockerParse(lb.iterator)

  m.mark

  def pr(str: String): String = {
    println(str);
    str
  }

  def baseParse(iterator: Iterable[String]): Iterable[Array[String]] = {
    iterator.filter(_.contains("   "))
      .map(_.split("   "))
  }

  def baseParseOption(iterator: Iterable[String]): Iterable[Array[String]] = {
    val lb = mutable.ArrayBuffer[String]()

    def addOrContinuLast(b: mutable.ArrayBuffer[String], s: String): mutable.ArrayBuffer[String] = {

      if (s.startsWith("-") || s.startsWith("--")) {
        b += s
      } else if (b.nonEmpty) {
        b(b.size - 1) = b.last + " " + s
      }
      b

    }

    iterator.map(_.trim).foldLeft(lb)(addOrContinuLast)

      .map(_.split("   ")).toIterable
  }

  def complete(c: Command): Command = {
    val (opt, descsUsage, usages) = options(c)
    c.copy(
      options = Some(opt),
      usage = Some(usages),
      usageDesc = Some(descsUsage)
    )
  }

  def getCommand: List[Command] = {
    baseParse(m.getFrom(t_cmds)).map((e) => Command(e(0).trim(), e.toIterator.slice(1, e.length).mkString.trim)).map(complete).toList
  }

  def createScalaCommand: String = {
    s"""
       |package bon.jo
       |import bon.jo.model._
       |object DockerCommands{
       |
      |  ${getCommand.map(Command.toScala(_)).mkString("\n")}
       | val all : Map[String,Command] = Map(${getCommand.map(e => s"""("${e.cmd}" -> ${Command.objectScalaName(e)})""").mkString(", ")})
       |}
       |
      |
      |
      |
     """.stripMargin

  }


  def options(command: Command): (List[CommandOption], List[UsageDesc], String /*usage*/ ) = {
    val scanneur = command.helpIteratorString
    scanneur.mark

    val clean = scanneur.get(t_usage).get.toString.replace("Usage:", "").trim()

    val desc = model.desc(clean)

    (baseParseOption(scanneur.getFrom(t_options)).map((e) => CommandOption(e(0).trim(), e.toIterator.slice(1, e.length).mkString.trim)).toList, desc, clean)

  }

  def runDockerHelp(filRet: File): Unit = {
    val pb = new ProcessBuilder();
    pb.redirectOutput(filRet)
    pb.redirectError(filRet)
    pb.command("docker", "--help")
    val p = pb.start()
    p.waitFor()
  }
}


 
