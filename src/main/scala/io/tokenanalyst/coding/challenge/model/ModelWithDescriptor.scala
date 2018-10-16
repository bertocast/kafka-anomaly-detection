package io.tokenanalyst.coding.challenge.model

import java.io.DataOutputStream

import scala.util.Try

case class ModelWithDescriptor(model: Model, descriptor: ModelToServe){}

object ModelWithDescriptor {

  def fromModelToServe(descriptor : ModelToServe): Try[ModelWithDescriptor] = Try{
    println(s"New model - $descriptor")
    ModelWithDescriptor(ADModel.create(descriptor), descriptor)
  }

  def readModel(input : String) : Option[Model] = {
    // TODO
    None
  }

  def writeModel(output : DataOutputStream, model: Model) : Unit = {
    // TODO
    None
  }
}
