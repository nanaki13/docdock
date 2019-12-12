package bon.jo.commandapi

import java.net.URLEncoder

import bon.jo.WebServer
import bon.jo.commandapi.Repo.FileRepo
import bon.jo.util._
import scala.collection.mutable
import bon.jo

trait CommandMemo {
  def saveMemo(name: String, cmd: List[String]): (String, List[String])

  def getMemo(name: String)

  def allMemo: Iterator[(String, List[String])]

  def allAsTextLink(f: ((String, List[String])) => Boolean): Iterator[(String, String)] = allMemo.filter(f).map(z => (z._1, CommandMemo.getAsLink(z._2)))
}

case class CommandMemoElement(id: Int, name: String, cmd: List[String])

case class CommandMemoElements(elements: List[CommandMemoElement])

object CommandMemo {
  def getAsLink(name: List[String]) = {
    val cmdBase = name.head
    val after = name.tail
    val cmdDocker = after.head
    val opts = after.tail
    WebServer.cmdPathString + "/" + cmdDocker + "/run?options=" + URLEncoder.encode(opts.mkString(","), "utf-8")
  }

}

case class InMemmoryCommandMemo(datas: mutable.Map[String, List[String]] = mutable.Map()
                               ) extends CommandMemo {
  override def saveMemo(name: String, cmd: List[String]): (String, List[String]) = {
    val t = name -> cmd
    datas += t
    t
  }

  override def getMemo(name: String): Unit = datas.get(name)

  override def allMemo: Iterator[(String, List[String])] = datas.iterator
}

class FileCommandMemo extends FileRepo[String, (String, List[String])](path("memo"), (e) => e._1) with CommandMemo {
  override def saveMemo(name: String, cmd: List[String]): (String, List[String]) = save((name, cmd))

  override def getMemo(name: String): Unit = get(name)

  override def allMemo: Iterator[(String, List[String])] = {
    println(ctx.allAdresses)
    (ctx.allAdresses.keys flatMap get).iterator
  }
}
