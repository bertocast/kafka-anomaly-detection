package io.tokenanalyst.coding.challenge.model

import io.tokenanalyst.coding.challenge.model.tokenrecord.TokenRecord

trait Model {
  def score(record: TokenRecord): (Double, Double)
  def cleanup(): Unit
  def getType: Long
}
