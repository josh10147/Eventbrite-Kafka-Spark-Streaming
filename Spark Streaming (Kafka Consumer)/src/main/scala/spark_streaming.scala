import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{ArrayType, StringType, StructField, StructType}
import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent


object spark_streaming {

  def main(args: Array[String]): Unit = {

    // Spark Config
    val conf = new SparkConf()
      .setAppName("kafka-spark-streaming")
      .setMaster("local[*]")

    val ssc = new StreamingContext(conf, Seconds(7))

    // Kafka Config
    val topics = List("eventbrite").toSet

    val kafkaParams = Map(
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "consumer-group", // Your consumer group
      "auto.offset.reset" -> "earliest")

    // Getting the Data from Kafka into Dstream Object
    val kafka_stream_Dstream = KafkaUtils.createDirectStream[String,String](
      ssc,
      PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))

    val lower_Dstream = kafka_stream_Dstream.map(record => record.value().toString)

    lower_Dstream.foreachRDD(rddRaw => {

      //Spark Session
      val spark = SparkSession.builder.config(rddRaw.sparkContext.getConf).getOrCreate()

      //User-Defined Schema
      val eventbriteSchema = StructType(List(
        StructField("events", ArrayType(
          StructType(List(
            StructField("id", StringType, true),
            StructField("name", StructType(List(
             StructField("text",StringType, true)
            )),true),
            StructField("start", StructType(List(
              StructField("utc", StringType, true)
            )), true),
            StructField("venue", StructType(List(
              StructField("address", StructType(List(
                StructField("city", StringType, true)
              )), true)
            )), true),
            StructField("subcategory", StructType(List(
              StructField("name", StringType, true)
            )), true)
          ))
        ), true)
      ))

      //Read JSON data from RDDs & create a DataFrame using User-Defined Schema
      val df = spark.read
        .schema(eventbriteSchema)
        .json(rddRaw)

      //Create a new DataFrame using explode and selecting specific columns
      val df2 = df
        .select(explode(df("events")).alias("events"))
        .select(col("events.id").alias("event_id"),
          col("events.name.text").alias("event_name"),
          col("events.start.utc").alias("date_time_utc"),
          col("events.venue.address.city").alias("event_city"),
          col("events.subcategory.name").alias("sub-category_name"))
        .show(10, false)

})
ssc.start()
ssc.awaitTermination()
}

}