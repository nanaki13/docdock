package bon.jo.commandapi

import bon.jo.Utils
import bon.jo.model.{Command, InErr}

trait View[T] {
  def manage(): T

  def imageList(cts: Iterable[Utils.ImagesRaw]): T

  def commandRun(ret: Int, inErr: InErr): T

  def containerList(cts: Iterable[Utils.ContainerPs]): T

  def commandHelp(c: Command): T
}
