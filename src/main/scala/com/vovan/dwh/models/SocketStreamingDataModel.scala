package com.vovan.dwh.models

/**
  * Created by Volodymyr Roman on 27.11.2016.
  */
case class SocketStreamingDataModel
(
  // TODO
  val transformer_id: Int,
  val generation_timestamp: String,
  val losses: Double,
  val electric_power: Int,
  val was_enabled: Boolean,
  val log_id: Int
)
