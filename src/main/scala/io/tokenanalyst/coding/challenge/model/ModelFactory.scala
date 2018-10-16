package io.tokenanalyst.coding.challenge.model

trait ModelFactory {
  def create(input: ModelToServe): Model
  def restore(average: Double, stdev: Double): Model
}