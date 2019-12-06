name := """local-data"""
lazy val commonSettings = Seq(
  organization := "bon.jo",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.13.1"
)
version := "1.0-SNAPSHOT"
lazy val root = (project in file(".")).settings(
  commonSettings,
 // watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)
//.dependsOn(wr).enablePlugins(PlayScala)
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies +="com.typesafe.akka" %% "akka-http"   % "10.1.10" 
libraryDependencies +="com.typesafe.akka" %% "akka-stream" % "2.5.23" 
libraryDependencies += "com.typesafe" % "config" % "1.4.0"
// libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
// libraryDependencies += "com.h2database" % "h2" % "1.4.196"
// libraryDependencies +="net.codingwell" %% "scala-guice" % "4.2.6"

enablePlugins(JavaAppPackaging)
mainClass in Compile := Some("bon.jo.WebServer")