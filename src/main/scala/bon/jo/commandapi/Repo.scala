package bon.jo.commandapi

import java.nio.file.Path

import scala.collection.mutable

trait Repo[Id, A] {
  def get(id: Id): Option[A]

  def save(a: A): A

  def delete(a: A): Boolean

  def id(a: A): Id

  def get(iterable: Iterable[Id]): Iterable[Option[A]] = iterable.map(this.get)

  def save(a: Iterable[A]): Iterable[A] = a.map(this.save)

  def flush : Unit
}

trait InMemmoryRepo[Id, A] {
  this: Repo[Id, A] =>
  val datas = mutable.Map[Id, A]()
  override def flush : Unit = {}
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

trait HashId[ A]{
  this: Repo[Int, A] =>
  override def id(a: A): Int = a.hashCode()
}

trait FileRepo[Id, A]{
  this: Repo[Id, A] =>
  def path : Path

}

trait Registery[Id,Adress]{
  def getAdress(id : Id) :Adress
}