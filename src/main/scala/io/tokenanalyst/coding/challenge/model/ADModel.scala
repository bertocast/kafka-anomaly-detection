package io.tokenanalyst.coding.challenge.model

import io.tokenanalyst.coding.challenge.model.modeldescriptor.ModelDescriptor
import io.tokenanalyst.coding.challenge.model.tokenrecord.TokenRecord

class ADModel(average: Double, stdev: Double) extends Model {



  override def score(record: TokenRecord): (Double, Double) = {
    val dif = math.abs(record.tx.get.weiValue - average)
    (dif - 2 * stdev, record.tx.get.weiValue)
  }

  override def cleanup(): Unit = {}


  override def getType: Long = ModelDescriptor.DATATYPE_FIELD_NUMBER
}

object ADModel extends ModelFactory {

  override def create(input: ModelToServe): Model = {
    new ADModel(input.movingAvg, input.movingSd)
  }

  override def restore(average: Double, stdev: Double): Model = new ADModel(average, stdev)
}
