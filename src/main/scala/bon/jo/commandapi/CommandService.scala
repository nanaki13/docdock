package bon.jo.commandapi

import bon.jo.Utils.{ContainerPs, ImagesRaw}
import bon.jo.model.InErr
import bon.jo.{DockerCommands, Utils}

trait CommandService {

  val d = DockerCommands



  def getImage: Iterable[ImagesRaw] = {
    implicit val out = InErr()
    val ret = d.`d_images`.run(out)
    if (ret == 0) {
      Utils.parseImageLs(out.out.toString())
    } else {
      InErr.throwException(ret)
    }
  }

  def getContainer: Iterable[ContainerPs] = {
    implicit val out = InErr()
    implicit val m = None
    val ret = d.`d_ps`.run(d.`d_ps`.`all`)
    val ct =  if (ret == 0) {
      Utils.parsePss(out.out.toString())
    } else {
      InErr.throwException(ret)
    }
    implicit val ev = (e : ImagesRaw)=> (e.id,e)
    val imgs = getImage.map(e=>(e.id,e)).toMap
    ct.map(c => c.copy(imagesRaw = imgs.get(c.image) ))

  }


}

object CommandService extends CommandService {

  sealed trait Context {
    def live: Boolean
  }

  object Live extends Context {
    override def live: Boolean = true
  }

  object NonLive extends Context {
    override def live: Boolean = false
  }

}
