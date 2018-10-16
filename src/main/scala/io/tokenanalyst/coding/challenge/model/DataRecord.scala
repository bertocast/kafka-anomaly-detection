package io.tokenanalyst.coding.challenge.model

import io.tokenanalyst.coding.challenge.model.tokenrecord.TokenRecord
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.util.Try

object DataRecord {


  def fromJson(message: String): Try[TokenRecord] =  Try {

    implicit val formats = DefaultFormats

    val json = parse(message)
    json.camelizeKeys.extract[TokenRecord]

  }

}
