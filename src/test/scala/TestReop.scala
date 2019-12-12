import java.nio.file.Paths
import java.time.Instant

import bon.jo.commandapi.Repo.FileRepo
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
class TestReop extends AnyFlatSpec with Matchers{



  val p = Paths.get("userRepo")
  p.toFile.mkdirs()
  p.toFile.listFiles().map(f=>(f.getName,f.delete())).foreach(e=>{
    val(f,del) = e
    println(s"$f : delete = $del")
  })
  val repo = new FileRepo[String, User]( p,(u)=>u.uid)
  val ul =  for(i <-  1 to 50) yield  User(i.toString,i.toString)
  try{
   val save =  ul map repo.save
   // repo.close()
  //  repo.ctx.flushOut
 //   repo.ctx.closeAllOut()
    val ids = ul map(_.uid)


    val fromRepo =  ids map repo.get


   " users" should " be the same" in {
     {fromRepo.map(_.get)} should be ( ul)
   }
  }catch {
    case e : Exception => e.printStackTrace()
  }finally {
    repo.close()
  }



}

case class User(uid: String, name: String)