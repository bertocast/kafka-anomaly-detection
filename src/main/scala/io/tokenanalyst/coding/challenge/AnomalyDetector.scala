package io.tokenanalyst.coding.challenge

import java.util
import java.util.Properties

import com.lightbend.kafka.scala.streams.StreamsBuilderS
import io.tokenanalyst.coding.challenge.configuration.kafka.ApplicationKafkaParameters._
import io.tokenanalyst.coding.challenge.model.{DataRecord, ModelToServe, ModelWithDescriptor}
import io.tokenanalyst.coding.challenge.store.ModelStateSerde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.state.Stores
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

object AnomalyDetector {


  def main(args: Array[String]): Unit = {
    val streamsConfiguration = new Properties
    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "anomaly-detection-server")
    streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, DATA_GROUP)
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER)
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass)
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass)


    val streams: KafkaStreams = setupStreams(streamsConfiguration)

    streams.start()

  }

    def setupStreams(streamsConfiguration: Properties): KafkaStreams = {

      val logConfig = new util.HashMap[String, String]
      val storeSupplier = Stores.inMemoryKeyValueStore(STORE_NAME)
      val storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.Integer, new ModelStateSerde).withLoggingEnabled(logConfig)


      val builder = new StreamsBuilderS

      val data  = builder.stream[String, String](DATA_TOPIC)
      val models  = builder.stream[String, String](MODELS_TOPIC)

      builder.addStateStore(storeBuilder)


      data
        .mapValues(value => DataRecord.fromJson(value))
        .filter((_, value) => value.isSuccess)
        .transform(() => new DataProcessor, STORE_NAME)
        .mapValues(value => {
          if(value.processed) println(s"Calculated distance - ${value.distance} calculated in ${value.duration} ms")
          // else println("No model available - skipping")
          value
        })

      models
        .mapValues(value => ModelToServe.fromJson(value))
        .filter((_, value) => value.isSuccess)
        .mapValues(value => ModelWithDescriptor.fromModelToServe(value.get))
        .filter((_, value) => value.isSuccess)
        .process(() => new ModelProcessor, STORE_NAME)

      val topology = builder.build
      println(topology.describe)

      new KafkaStreams(topology, streamsConfiguration)

    }

}
