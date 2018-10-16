package io.tokenanalyst.coding.challenge.model


import java.io.DataOutputStream

final case class ModelToServeStats(
  name: String,
  description: String,
  since: Long,
  usage: Long = 0,
  duration: Double = 0.0,
  min: Long = 0,
  max: Long = 0) {

  def incrementUsage(execution: Long): ModelToServeStats = {
    copy(
      usage = usage + 1,
      duration = duration + execution,
      min = if (execution < min) execution else min,
      max = if (execution > max) execution else max)
  }
}

object ModelToServeStats {
  val empty = ModelToServeStats("None", "None", 0)

  def apply(m: ModelToServe): ModelToServeStats =
    ModelToServeStats(m.name, m.description, System.currentTimeMillis())

  def readServingInfo(input: String) : Option[ModelToServeStats] = {
    // TODO
    None
  }

  def writeServingInfo(output: DataOutputStream, servingInfo: ModelToServeStats ): Unit = {
    // TODO
  }
}

