package bon.jo

import java.nio.file.{Path, Paths}

package object util {
     def currentDir: Path = Paths.get(".")
     def path(str :String): Path = {
          val ret = Paths.get(str)
          val f = ret.toFile
          if(!f.exists()){
               f.mkdirs()
          }
          ret
     }
}
