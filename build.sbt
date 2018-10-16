name := "anomaly-detection"

version := "0.1"

scalaVersion := "2.11.12"

val kafka_streams_scala_version = "0.1.0"
val kafkaVersion = "2.0.0"

val workaround = {
  sys.props += "packaging.type" -> "jar"
  ()
}

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies += "javax.ws.rs" % "javax.ws.rs-api" % "2.1" artifacts Artifact("javax.ws.rs-api", "jar", "jar")

libraryDependencies ++= Seq("com.lightbend" %%
  "kafka-streams-scala" % kafka_streams_scala_version)

libraryDependencies ++= Seq("org.apache.kafka" %%
  "kafka" % kafkaVersion)

libraryDependencies ++= Seq("org.apache.kafka" %
  "kafka-streams" % kafkaVersion)

libraryDependencies += "org.jpmml" % "pmml-evaluator" % "1.4.3"

libraryDependencies += "org.jpmml" % "pmml-evaluator-extension" % "1.4.3"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.1"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.6.1"



enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
mainClass in Compile := Some("io.tokenanalyst.coding.challenge.Main")