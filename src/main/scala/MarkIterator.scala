package bon.jo

import scala.collection.mutable

trait MarkIterator {
  def me: Iterator[String]

  val readed = mutable.ListBuffer[String]()
  readed ++= me
  var marks: Map[String, Int] = Map()

  def findFirst(str: String): Option[Int] = {
    readed.zipWithIndex.find(_._1.contains(str)).map(_._2)
  }

  def createMark(str: String): Unit = {

    marks += (str -> findFirst(str).get)

  }

  def createMarkIfExists(str: String): Unit = {

    findFirst(str) match {
      case Some(value) => marks += (str -> value)
      case _ =>
    }


  }

  def getFrom(str: String):List[String] = {
    marks.get(str) match {
      case Some(value) => readed.slice(marks(str), readed.size).toList
      case None => Nil
    }

  }

  def getFrom(str: String, toEx: String):List[String] = {
    readed.slice(marks(str), marks(toEx)).toList
  }

  def get(mark: String): Option[String] = marks.get(mark) match {
    case Some(value) => Some(readed(value))
    case None => None
  }

}