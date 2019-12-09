package bon.jo.commandapi

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import spray.json._
trait JsonOut extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val cmdFormat = jsonFormat3(CommandMemoElement)
  implicit val cmdsFormat = jsonFormat1(CommandMemoElements)

}
