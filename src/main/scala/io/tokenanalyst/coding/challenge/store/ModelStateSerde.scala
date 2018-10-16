package io.tokenanalyst.coding.challenge.store

import java.io.ByteArrayOutputStream
import java.util

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}


class ModelStateSerde extends Serde[StoreState] {

  private var mserializer = new ModelStateSerializer()
  private var mdeserializer = new ModelStateDeserializer()

  override def deserializer() = mdeserializer

  override def serializer() = mserializer

  override def configure(configs: util.Map[String, _], isKey: Boolean) = {}

  override def close() = {}
}

object ModelStateDeserializer {
}

class ModelStateDeserializer extends Deserializer[StoreState] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def close(): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): StoreState = ???
}

class ModelStateSerializer extends Serializer[StoreState] {

  private val bos = new ByteArrayOutputStream()

  override def serialize(topic: String, state: StoreState): Array[Byte] = {
    null
  }

  override def close(): Unit = {}

  override def configure(configs: util.Map[String, _], isKey: Boolean) = {}
}
