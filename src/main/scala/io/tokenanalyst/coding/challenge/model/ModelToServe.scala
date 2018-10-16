package io.tokenanalyst.coding.challenge.model


import io.tokenanalyst.coding.challenge.model.modeldescriptor.ModelDescriptor
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.util.Try

object ModelToServe {
  def fromJson(message: String): Try[ModelToServe] =  Try {
    implicit val formats = DefaultFormats
    val json = parse(message)
    val model = json.camelizeKeys.extract[ModelDescriptor]
    ModelToServe(model.name, model.description, model.movingAverage, model.movingSd, model.dataType)
  }
}
case class ModelToServe(name: String, description: String,
                        movingAvg: Double, movingSd: Double, dataType: String) {}

case class ServingResult(processed : Boolean, distance: Double = .0, weiValue:Double = .0, duration: Long = 0l)

object ServingResult{
  val noModel = ServingResult(false)
  def apply(distance: Double, weiValue: Double, duration: Long): ServingResult = ServingResult(true, distance, weiValue, duration)
}



