import java.util.Properties
import org.apache.kafka.clients.producer._
import scalaj.http._

object kafka_prod {

  def main(args: Array[String]): Unit = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    val topics = "eventbrite"

    val url1 = "https://www.eventbriteapi.com/v3/events/search/?location.address=187+NW+28th+St%2C+Miami%2C+FL+33127&location.within=1mi&categories=103&include_adult_events=on&expand=venue,category,subcategory&token=VLE67CFPLOTDMS3NJC56"
    val response: HttpResponse[String] = Http(url1).header("Accept", "application/json").asString //convert api url to json
    val response_body = response.body

    //println(response_body)
    //println(record)

    while (true) {

      val record = new ProducerRecord[String, String](topics ,response_body)
      producer.send(record)

      }

    producer.close()

  }

}
