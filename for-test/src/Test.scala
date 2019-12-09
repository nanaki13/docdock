import scala.io.Source

object Test extends App {


  //read the file and check that the test number is correct
  val pathFile = "./for-test/in.txt"
  val l = Source.fromFile(pathFile).getLines().toList

  val testCoutn = l.head
  val toBeTest = l.tail

  if(testCoutn.toInt != toBeTest.length){
    throw new IllegalStateException(s"Invalid file ${pathFile} : number of test cases ${testCoutn} is not equals to number of test line : ${toBeTest.length}")
  }

  /**
    * Test method :  return the last before sleep or INSOMNIA
    * @param i
    * @return
    */
  def test(i: Int) = {
    var digit = Set[Short]()
    var li = i
    var prev = -1
    while(digit.size < 10 && prev != li){
      li.toString.foreach( c=>{
        digit +=  c.toString.toShort
      })
      prev = li

      li = li*2
    }
    if(prev == li){
      "INSOMNIA"
    }else{
      prev
    }
  }
  // call the method on toBeTest
  for (i <- toBeTest.zipWithIndex) {
    println(s"Case #${i._2+1} : ${test(i._1.toInt)}")

  }
}
