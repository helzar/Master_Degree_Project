package com.vovan.dwh

import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.{Duration, LocalDateTime}

import com.datastax.spark.connector._
import com.mongodb.hadoop.MongoInputFormat
import com.vovan.dwh.models.{LogsCoreAreaModel, MongoDataModel}
import org.apache.hadoop.conf.Configuration
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.bson.BSONObject

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Volodymyr Roman on 27.11.2016.
  */
// TODO: should be done as Streaming, see insertionTimestamp field comment in PowerConsumption cass
// TODO: add property file
// TODO: Refactor this !!!!!
object MongoDbEtlDriver {
  private val DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
  private val LoggingInterval = Duration.ofMinutes(1)
  private val NumberOfIntervalsOnBorder = 60

  val mongoHost = "localhost"
  val mongoDb = "local"
  val mongoCollection = "energy_data"


  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val sparkConf = new SparkConf().setAppName("MongoETL").setMaster("local").set("spark.cassandra.connection.host", "localhost")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    val mongoConfig = new Configuration()
    mongoConfig.set("mongo.input.uri",
      s"mongodb://${mongoHost}:27017/${mongoDb}.${mongoCollection}")
    // TODO: Filter by insertion timestamp
    //    mongoConfig.set("mongo.input.query",
    //      "{fild_1: \"value_1\", f_2: \"v_2\"}")

    val rdd = sc.newAPIHadoopRDD(
      mongoConfig, // Configuration
      classOf[MongoInputFormat], // InputFormat
      classOf[Object], // Key type
      classOf[BSONObject]) // Value type

    val prepared = rdd
      .map(kv => kv._2)
      .map(v => MongoDataModel(
        v.get("transformer_id").asInstanceOf[Int],
        v.get("generation_timestamp").asInstanceOf[String],
        v.get("losses").asInstanceOf[Double],
        v.get("electric_power").asInstanceOf[Int],
        v.get("was_enabled").asInstanceOf[Boolean],
        v.get("log_id").asInstanceOf[Int],
        v.get("insertion_timestamp").asInstanceOf[String])
      )

    // TODO: remove comment
    //    prepared.saveToCassandra("main_storage", "power_consumption_row")

    prepared
      .map(m => (m.transformer_id, m))
      .groupByKey()
      .flatMap(kv => transformLogsToCoreAreaFormat(kv._1, kv._2))
      .saveToCassandra("main_storage", "power_consumption_core")



    // TODO: based on Mongo-Spark library and Spark 2.x
    //    Logger.getLogger("org").setLevel(Level.OFF)
    //    Logger.getLogger("akka").setLevel(Level.OFF)

    //    val warehouseLocation = "file:/E:/Java/workspase_IDEA/Master_Degree_Project/spark-warehouse"
    //    val sparkSession = SparkSession.builder
    //      .master("local")
    //      .appName("MongoETL")
    //      .config("spark.sql.warehouse.dir", warehouseLocation)
    //      .getOrCreate()
    //    val builder = MongodbConfigBuilder(
    //      Map(
    //        Host -> List("localhost:27017"),
    //        Database -> "local",
    //        Collection ->"energy_data"
    //      )
    //    )
    //    val readConfig = builder.build()
    //    val mongoRDD = sparkSession.sqlContext.fromMongoDB(readConfig)
    //    mongoRDD.createTempView("students")
    //    val dataFrame = sparkSession.sql("SELECT name, age FROM students")
    //    dataFrame.show
  }

  def transformLogsToCoreAreaFormat(key: Int, logsIter: Iterable[MongoDataModel]) = {
    val logs = logsIter.toArray
    val sortedLogs = logs.sortBy(log => log.generation_timestamp)

    val result = ArrayBuffer[LogsCoreAreaModel]()

    var lastTimestamp: Option[LocalDateTime] = None
    for (log <- sortedLogs) {
      if (lastTimestamp.isEmpty) {
        var currentTimestamp = LocalDateTime.parse(log.generation_timestamp, DateFormatter).plusSeconds(LoggingInterval.getSeconds * (1 - NumberOfIntervalsOnBorder))
        for (i <- 1 to NumberOfIntervalsOnBorder) {
          result += LogsCoreAreaModel(log.transformer_id, DateFormatter.format(currentTimestamp), log.losses, log.electric_power / NumberOfIntervalsOnBorder, log.was_enabled, log.log_id)
          currentTimestamp = currentTimestamp.plusSeconds(LoggingInterval.getSeconds)
        }
        lastTimestamp = Some(LocalDateTime.parse(log.generation_timestamp, DateFormatter))
      } else {
        var currentTimestamp = lastTimestamp.get.plusSeconds(LoggingInterval.getSeconds)
        val logTimestamp = LocalDateTime.parse(log.generation_timestamp, DateFormatter);
        val intervalsNumber = (currentTimestamp.until(logTimestamp, ChronoUnit.SECONDS)) / LoggingInterval.getSeconds
        while (logTimestamp.isAfter(currentTimestamp) || logTimestamp.isEqual(currentTimestamp)) {
          result += LogsCoreAreaModel(log.transformer_id, DateFormatter.format(currentTimestamp), log.losses, log.electric_power / intervalsNumber, log.was_enabled, log.log_id)
          currentTimestamp = currentTimestamp.plusSeconds(LoggingInterval.getSeconds)
        }
        lastTimestamp = Some(logTimestamp)
      }
    }
    result.toArray
  }
}
