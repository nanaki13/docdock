package bon.jo

import bon.jo.model.Command

object TestCommand extends App {


  // import DockerCommands._
  //all.keys.foreach(println)
  //DockerCommands.d_ps.run

  Command.htmlFile(DockerAnalyse(List("--help")).getCommand)
  //createFile
  Utils.noMatch.foreach {
    println(_)
  }
  //    println(DockerAnalyse(List("--help")).createScalaCommand)
}