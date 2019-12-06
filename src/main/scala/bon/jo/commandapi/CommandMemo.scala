package bon.jo.commandapi

import java.net.URLEncoder

import bon.jo.WebServer

import scala.collection.mutable

trait CommandMemo {
   def save(name : String,cmd : List[String])
   def get(name : String)

  def all : Iterator[(String,List[String])]
  def allAsTextLink : Iterator[(String,String)] = all.map(z=> (z._1,CommandMemo.getAsLink(z._2)))
}

object CommandMemo{
  def getAsLink(name : List[String]) = {
    val cmdBase = name.head
    val after = name.tail
    val cmdDocker = after.head
    val opts = after.tail
    WebServer.cmdPathString+"/"+cmdDocker+"/run?options="+URLEncoder.encode( opts.mkString(","),"utf-8")
  }

}

case class InMemmoryCommandMemo(datas : mutable.Map[String,List[String]] = mutable.Map()
                               ) extends CommandMemo {
  override def save(name: String, cmd: List[String]): Unit = datas += (name ->cmd)

  override def get(name: String): Unit = datas.get(name)

  override def all: Iterator[(String, List[String])] = datas.iterator
}
