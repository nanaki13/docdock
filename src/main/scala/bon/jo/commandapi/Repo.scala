package bon.jo.commandapi

import java.io._
import java.nio.file.{Path, Paths}

import bon.jo.commandapi.Repo.Index.IndexContext

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


object Repo {


  trait InMemmoryRepo[Id, A] {
    this: Repo[Id, A] =>
    val datas = mutable.Map[Id, A]()

    override def flush: Unit = {}

    override def get(id: Id): Option[A] = datas.get(id)

    override def save(a: A): A = {
      datas += (id(a) -> a)
      a
    }

    override def delete(a: A): Boolean = {
      val idp = id(a)
      if (datas.contains(idp)) {
        datas -= idp
        true
      } else {
        false
      }
    }
  }

  trait HashId[A] {

    def hash(a: A): Int = a.hashCode()
  }

  object IdContext {

    implicit object IdStringContext extends IdContext[String] {
      override implicit def idReader: DataInputStream => String = {
        (d) => d.readUTF()
      }
      override implicit def idWriter: (String, DataOutputStream) => Unit = {
        (s, d) => d.writeUTF(s)
      }
    }

    implicit object IdIntContext extends IdContext[Int] {
      override implicit def idReader: DataInputStream => Int = {
        (d) => d.readInt()
      }
      override implicit def idWriter: (Int, DataOutputStream) => Unit = {
        (s, d) => d.writeInt(s)
      }
    }

  }

  trait IdContext[Id] {
    implicit def idReader: (DataInputStream) => Id

    implicit def idWriter: (Id, DataOutputStream) => Unit
  }
   case class FileRepo[Id, A](override val  path: Path,   idF :( A)=> Id )( implicit val idContext: IdContext[Id]) extends FileRepoT[Id, A]{
     override def id(a: A): Id = idF(a)
   }

  trait FileRepoT[Id, A] extends Repo[Id, A] {

    implicit def idContext: IdContext[Id]

    def closeWriters() = ctx.closeAllOut()

    def path: Path

    implicit val ctx = IndexContext[Id](10, path, "idx")

    object idx extends Index[Id] {


    }

    idx.readAllAdress

    def close() = {
      ctx.closeAllIn()
      ctx.closeAllOut()
      oReader match {
        case Some(value) => value.close()
        case None =>
      }
      oWriter match {
        case Some(value) => value.close()
        case None =>
      }
      datas.close()
    }


    val f = path.resolve("f.data").toFile


    def asIs(datas: RandomAccessFile): InputStream = () => datas.read()

    def asOs(datas: RandomAccessFile): OutputStream = (b: Int) => datas.write(b)

    def buffered(is: InputStream): InputStream = new BufferedInputStream(is)

    def bis(datas: RandomAccessFile): InputStream = asIs(datas)

    val datas = new RandomAccessFile(path.resolve("f.data").toFile, "rw")
    var oReader = createReader()

    def createReader(): Option[ObjectInputStream] = if (f.exists()) {
      try {
        datas.seek(0)
        Some(new ObjectInputStream(bis(datas)))
      } catch {
        case _: EOFException => None
      }

    } else {
      None
    }

    def createWriter(): Option[ObjectOutputStream] = if (f.exists()) {
      try {
        datas.seek(0)
        Some(new ObjectOutputStream(bos(datas)))
      } catch {
        case _: EOFException => None
      }

    } else {
      None
    }

    def buffered(is: OutputStream): OutputStream = new BufferedOutputStream(is)

    def bos(datas: RandomAccessFile): OutputStream = asOs(datas)

    var oWriter: Option[ObjectOutputStream] = None

    var first = true

    override def get(id: Id): Option[A] = {
      if (oReader.isEmpty) {
        oReader = createReader()

      }
      ctx.allAdresses.get(id) match {
        case Some(idValue) => {
          datas.seek(idValue)
          //   val reader = oReader(datas)
          try {
            val ret = oReader.get.readObject()

            Some(ret.asInstanceOf[A])
          } catch {
            case e: Exception => if (first) {
              println(id, idValue);
              e.printStackTrace();
              println(datas.length());
              first = false
            };
              None
          }

          //    reader.close()

        }
        case _ => None
      }

    }

    def _save(a: A): Long = {
      if (oWriter.isEmpty) {

        oWriter = createWriter()

      }
      val ret = datas.length()
      datas.seek(ret)

      oWriter.get.writeObject(a)
      oWriter.get.flush()
      ret
    }

    override def save(a: A): A = {
      val idp = id(a)
      val adress = _save(a)

      if (ctx.allAdresses.keys.exists(_ == idp)) {
        idx.save(idp, adress)
        ctx.allAdresses += (idp -> adress)
      } else {
        idx.save(idp, adress)
        ctx.allAdresses += (idp -> adress)
      }

      a
    }

    override def delete(a: A): Boolean = ???


    override def flush: Unit = {
      oWriter.get.flush()
    }
  }

  trait Registery[Id] {
    def getAdress(id: Id): Long
  }

  object Index {

    case class IndexContext[Id](
                                 modulo: Int = 1,
                                 dest: Path = Paths.get("."),
                                 baseFile: String = "idx",
                                 allAdresses: mutable.Map[Id, Long] = mutable.Map[Id, Long]())(implicit
                                                                                               idContext: IdContext[Id]

                               ) extends HashId[Id] {
      def save(idp: Id, adress: Long) = {
        val out = getOut(hash(idp))
        idContext.idWriter(idp, out)
        out.writeLong(adress)
      }


      var fs = Map[Int, DataOutputStream]()
      var is = Map[Int, DataInputStream]()


      //    def createOuts(): Unit = {
      //        for (i <- 0 until modulo) {
      //          val out = dest.resolve(s"$baseFile.$i.data")
      //          fs += (i -> oos(out))
      //        }
      //    }

      def createOuts(int: Int): DataOutputStream = {
        fs.get(int) match {
          case Some(v) => v
          case None => {

            val out = dest.resolve(s"$baseFile.$int.data")
            //      val o = new FileOutputStream(out.toFile, true)
            val o = oos(out)
            fs += (int -> o)
            o
          }
        }

      }

      def creatIns(): Unit = {
        for (i <- 0 until modulo) {
          val in = dest.resolve(s"$baseFile.$i.data")
          if (in.toFile.exists()) {
            is += (i -> ois(in))
          }

        }
      }

      def flushOut = fs.values.foreach(_.flush)

      def closeAllOut(): Unit = {
        //fs.values.foreach(_.flush)
        fs.values.foreach(_.close)
        fs = Map[Int, DataOutputStream]()
      }

      def closeAllIn(): Unit = {

        is.values.foreach(_.close)
        is = Map[Int, DataInputStream]()
      }

      def getOut(hash: Int): DataOutputStream = {

        val prefix = math.abs(hash) % modulo
        createOuts(prefix)

      }

      def getIn(hash: Int): Option[DataInputStream] = {
        val prefix = math.abs(hash) % modulo

        is.get(prefix)
      }

      def contains(id: Id): Future[Boolean] = {
        readAll().map(_.map(_._1).contains(id))
      }

      def readAll(): Future[Seq[(Id, Long)]] = {
        creatIns()

        val fs: Seq[Future[List[(Id, Long)]]] = for (i <- 0 until modulo) yield {
          Future {
            var notEnd = true
            var buff = mutable.ListBuffer[(Id, Long)]()

            while (notEnd) {
              getIn(i) match {

                case Some(in) => {
                  try {

                    val o = idContext.idReader(in)

                    val add = in.readLong()

                    val el: (Id, Long) = (o, add)

                    buff += el
                  } catch {
                    case _: EOFException => notEnd = false
                  }
                }
                case None => notEnd = false
              }
            }
            buff.toList
          }
        }
        Future.sequence(fs).map(_.flatten)

      }

      @inline
      def oos(path: Path): DataOutputStream = {
        val ret = new DataOutputStream(new FileOutputStream(path.toFile,true))
        ret
      }

      @inline
      def ois(path: Path): DataInputStream = new DataInputStream(new FileInputStream(path.toFile))
    }


  }

  trait Index[Id] {
    def close(implicit ic: IndexContext[Id]) = {
      ic.closeAllOut();
      ic.closeAllIn()
    }

    def flush(implicit ic: IndexContext[Id]) = ic.flushOut


    def save(idp: Id, adress: Long)(implicit ic: IndexContext[Id]) = {

      ic.save(idp, adress)
    }

    //    def update(idp: Id, adress: Long)(implicit ic: IndexContext[Id]) = {
    //      val out = ic.getOut(hash(idp))
    //      out.writeObject(idp.asInstanceOf[Id])
    //      out.writeLong(adress)
    //      out.flush()
    //    }

    def readAll(implicit ic: IndexContext[Id]): Future[Seq[(Id, Long)]] = {
      ic.readAll

    }

    def readAllAdress(implicit ic: IndexContext[Id]): Map[Id, Long] = {
      ic.allAdresses ++= Await.result(readAll, Duration.Inf).toMap
      ic.allAdresses.toMap
    }


  }

}

trait Repo[Id, A] {
  def get(id: Id): Option[A]

  def save(a: A): A

  def delete(a: A): Boolean

  def id(a: A): Id

  def get(iterable: Iterable[Id]): Iterable[Option[A]] = iterable.map(this.get)

  def save(a: Iterable[A]): Iterable[A] = a.map(this.save)

  def flush: Unit
}





