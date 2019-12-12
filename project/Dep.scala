
import sbt._
object Dep {
  lazy val testVersion = "3.1.0"
  val  scalaTest = "org.scalatest" %% "scalatest" % testVersion % "test"
}
