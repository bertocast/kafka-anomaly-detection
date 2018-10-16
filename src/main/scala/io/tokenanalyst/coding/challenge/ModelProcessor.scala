package io.tokenanalyst.coding.challenge

import java.util.Objects

import io.tokenanalyst.coding.challenge.configuration.kafka.ApplicationKafkaParameters
import io.tokenanalyst.coding.challenge.model.{ModelToServeStats, ModelWithDescriptor}
import io.tokenanalyst.coding.challenge.store.StoreState
import org.apache.kafka.streams.processor.{AbstractProcessor, ProcessorContext}
import org.apache.kafka.streams.state.KeyValueStore

import scala.util.Try

class ModelProcessor extends AbstractProcessor[String, Try[ModelWithDescriptor]] {

  private var modelStore: KeyValueStore[Integer, StoreState] = null

  import io.tokenanalyst.coding.challenge.configuration.kafka.ApplicationKafkaParameters._
  override def process (key: String, modelWithDescriptor: Try[ModelWithDescriptor]): Unit = {

    var state = modelStore.get(STORE_ID)
    if (state == null) state = new StoreState

    state.newModel = Some(modelWithDescriptor.get.model)
    state.newState = Some(ModelToServeStats(modelWithDescriptor.get.descriptor))
    modelStore.put(ApplicationKafkaParameters.STORE_ID, state)
  }

  override def init(context: ProcessorContext): Unit = {
    modelStore = context.getStateStore(STORE_NAME).asInstanceOf[KeyValueStore[Integer, StoreState]]
    Objects.requireNonNull(modelStore, "State store can't be null")
  }

}
