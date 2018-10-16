package io.tokenanalyst.coding.challenge.store

import io.tokenanalyst.coding.challenge.model.{Model, ModelToServeStats}

case class StoreState(var currentModel: Option[Model] = None, var newModel: Option[Model] = None,
                      var currentState: Option[ModelToServeStats] = None, var newState: Option[ModelToServeStats] = None){
  def zero() : Unit = {
    currentModel = None
    currentState = None
    newModel = None
    newState = None
  }
}

object StoreState{
  val noneExistent = StoreState(None, None, None, None)
  private val instance = new StoreState()
  def apply(): StoreState = instance
}
