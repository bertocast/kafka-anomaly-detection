package io.tokenanalyst.coding.challenge

import java.util.Objects

import io.tokenanalyst.coding.challenge.model.ServingResult
import io.tokenanalyst.coding.challenge.model.tokenrecord.TokenRecord
import io.tokenanalyst.coding.challenge.store.StoreState
import org.apache.kafka.streams.kstream.Transformer
import org.apache.kafka.streams.processor.ProcessorContext
import org.apache.kafka.streams.state.KeyValueStore

import scala.util.Try

class DataProcessor extends Transformer[String, Try[TokenRecord], (String, ServingResult)]{

  private var modelStore: KeyValueStore[Integer, StoreState] = null

  import io.tokenanalyst.coding.challenge.configuration.kafka.ApplicationKafkaParameters._

  override def transform(key: String, dataRecord: Try[TokenRecord]) : (String, ServingResult) = {

    var state = modelStore.get(STORE_ID)
    if (state == null) state = new StoreState

    state.newModel match {
      case Some(model) => {
        // close current model first
        state.currentModel match {
          case Some(m) => m.cleanup()
          case _ =>
        }
        // Update model
        state.currentModel = Some(model)
        state.currentState = state.newState
        state.newModel = None
      }
      case _ =>
    }
    val result = state.currentModel match {
      case Some(model) => {
        val start = System.currentTimeMillis()
        val quality = model.score(dataRecord.get)
        val duration = System.currentTimeMillis() - start
        state.currentState = state.currentState.map(_.incrementUsage(duration))
        ServingResult(true, quality._1, quality._2, duration)
      }
      case _ => {
        ServingResult(false)
      }
    }
    modelStore.put(STORE_ID, state)
    (key, result)
  }

  override def init(context: ProcessorContext): Unit = {
    modelStore = context.getStateStore(STORE_NAME).asInstanceOf[KeyValueStore[Integer, StoreState]]
    Objects.requireNonNull(modelStore, "State store can't be null")
  }

  override def close(): Unit = {}

  def punctuate(timestamp: Long): (Array[Byte], ServingResult) = null
}
