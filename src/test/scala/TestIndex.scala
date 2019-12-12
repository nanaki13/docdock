import bon.jo.commandapi.Repo.Index._
import bon.jo.commandapi.Repo.Index
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TestIndex extends AnyFlatSpec  with Matchers {
  {
    val a: Index[String] = new Index[String]() {}
    implicit val b = new IndexContext[String]()
    a.save("1", 12)
    a.save("2", 15)
    a.flush
    a.readAllAdress
    println(b.allAdresses)
    a.close


    a.save("3", 12)
    a.save("4", 15)
    a.flush
    a.readAllAdress
    println(b.allAdresses)
    a.close
  }


  {
    val d: Index[String] = new Index[String]() {}
    implicit val e = new IndexContext[String]()
    d.readAllAdress
    println(e.allAdresses)
    " index" should " be the same" in {
      {e.allAdresses} should be ( Map("1" -> 12, "2" -> 15, "3" -> 12, "4" -> 15))
    }
  }


}
