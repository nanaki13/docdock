
import java.io.FileWriter

import InOut._

import scala.io.Source

case class ToConsume(flow: Iterator[AS], end: () => Unit)

object InOut {
  type AS = Array[String]

  trait BaseRow {
    var headers: Array[String]
    var input: Array[String]
  }

  trait In extends Product {
    def printRow(input: Array[String]) = {
      for (i <- 0 until productArity) {
        print(s"${productElementName(i)} = ${input(i)}")
      }
      println()
    }

    def readFile(fileName: String): Iterator[Array[String]]

    def listenRead(fileName: String)(map: AS => AS): Iterator[AS] = readFile(fileName).map(map)

    def genRead(startRead: () => Iterator[AS])(map: AS => AS): Iterator[AS] = startRead().map(map)
  }

  trait Out {
    def readFiletoFile(in: In, inFile: String, fName: String): In = {
      val out = new FileWriter(inFile)
      in.listenRead(fName)(e => {
        out.append(s"${e}\n"); e
      })
      out.close()
      in
    }

    def toFile(in: In, startRead: () => Iterator[AS], fName: String): ToConsume = {
      val out = new FileWriter(fName)
      ToConsume(in.genRead(startRead)(e => {
        out.append(s"${e.mkString(";")}\n"); e
      }), out.close)
    }
  }

  trait CsvIn extends Product with BaseRow {

    this: In =>

    def readLineCsv(name: String)(implicit sep: String): Array[String] = {
      input = name.split(sep)
      input
    }

    override def readFile(name: String): Iterator[Array[String]] = readCsvFile(name)(";")

    def readCsvFile(name: String)(implicit sep: String): Iterator[Array[String]] = {
      val source = Source.fromFile(name)
      val it = source.getLines().map(readLineCsv)
      source.close()
      it
    }

    def readStringCsv(name: String)(implicit sep: String): Iterator[Array[String]] = {

      val it = name.split("\r\n|\n|\r").map(readLineCsv)
      it.iterator
    }

    override def productArity: Int = input.length

    override def productElementName(n: Int): String = headers(n)

    override def productElement(n: Int): Any = input(n)

    override def canEqual(o: Any): Boolean = {
      o match {
        case e: CsvIn => e.input == this.input
      }
    }

    override def productIterator: Iterator[Any] = input.iterator
  }


  trait Process {
    def tr(in: In): Out
  }

}

object Test extends App {

  object CsvReader extends In with CsvIn with Out {
    var headers: Array[String] = Array("id", "name")
    var input: Array[String] = Array()
  }

  CsvReader.readStringCsv("teoeo;sdfsdf\ndfdfg;rgrgrg")(";").map(CsvReader.printRow)

  def r(): Iterator[AS] = CsvReader.readStringCsv("teoeo;sdfsdf\ndfdfg;rgrgrg")(";")

  val dataFlow = CsvReader.toFile(CsvReader, r, "tmp.csv")
  val cnt = dataFlow.flow.foldLeft(0)((sum, _) => sum + 1)
  print(cnt)
  dataFlow.end()

}