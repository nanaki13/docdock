package bon.jo.commandapi

import java.io.{DataOutputStream, FileOutputStream, FileWriter, ObjectInputStream, ObjectOutputStream}
import java.nio.file.Path

import bon.jo.commandapi.Index.IndexContext

import scala.collection.mutable

trait Repo[Id, A] {
  def get(id: Id): Option[A]

  def save(a: A): A

  def delete(a: A): Boolean

  def id(a: A): Id

  def get(iterable: Iterable[Id]): Iterable[Option[A]] = iterable.map(this.get)

  def save(a: Iterable[A]): Iterable[A] = a.map(this.save)

  def flush: Unit
}

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
    val id = id(a)
    if (datas.contains(id)) {
      datas -= id
      true
    } else {
      false
    }
  }
}

trait HashId[A] {

  def id(a: A): Int = a.hashCode()
}

trait FileRepo[Id, A] {
  this: Repo[Id, A] =>
  def path: Path

}

trait Registery[Id] {
  def getAdress(id: Id): Long
}

object Index {

  case class IndexContext(modulo: Int, dest: Path, baseFile: String) {

    var fs = Map[Int, ObjectOutputStream]()
    var is = Map[Int, ObjectInputStream]()
    var read = false
    var write = false

    def createOuts(): Unit = {
      if (!write) {
        write = true
        for (i <- 0 until modulo) {
          val out = dest.resolve(s"$baseFile.$i.data")
          fs += (i -> file(out))
        }
      } else {
        throw new IllegalStateException("already created")
      }

    }

    def creatIns(): Unit = {
      if (!write) {
        write = true
        for (i <- 0 until modulo) {
          val out = dest.resolve(s"$baseFile.$i.data")
          fs += (i -> file(out))
        }
      } else {
        throw new IllegalStateException("already created")
      }

    }

    def closeAll(): Unit = {
      write = false
      fs.values.foreach(_.close)
      fs = Map[Int, ObjectOutputStream]()
    }

    def getOut(hash: Int): ObjectOutputStream = {
      val prefix = hash % modulo
      fs(prefix)
    }

    def readAll[Id](): Iterable[(Id, Long)] = {
      read = true
    }

  }

  @inline
  def file(path: Path): ObjectOutputStream = new ObjectOutputStream(new FileOutputStream(path.toFile))
}

trait Index[Id] extends HashId[Id] {


  def save(idp: Id, adress: Long)(implicit ic: IndexContext) = {
    val out = ic.getOut(id(idp))
    out.writeObject(idp)
    out.writeLong(adress)
  }

  def readAll(implicit ic: IndexContext): Unit = {
    ic.readAll
  }


}