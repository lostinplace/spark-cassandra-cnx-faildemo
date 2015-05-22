package fake

import com.datastax.spark.connector._
import org.apache.spark.streaming._
import com.datastax.spark.connector.streaming._
import org.apache.spark.{SparkConf, SparkContext}

object FakeDataStreamer {

  var failureHandler = (a :String, b: String ) => {1}

  def main(args: Array[String]) {
    if(args.length>0 && args(0) == "d"){
      println("-------------Attach debugger now!--------------")
      Thread.sleep(8000)
    }

    val config = new SparkConf()
      .setAppName("Fake Data Stream")

    val streamContext = new StreamingContext(config, Seconds(3) )

    generateReceiverStream(streamContext)

    streamContext.start()
    streamContext.awaitTermination()
  }

  def generateReceiverStream(ssc: StreamingContext): Unit = {

    val customReceiverStream = ssc.receiverStream[String](new FakeDataReceiver())

    val vsStream = customReceiverStream.filter(_.nonEmpty).map(FakeMessage(_))

    // your life would be much easier if you didn't camelCase your table field names in cql but instead just did
    // message_id, message_content, then you would not need to specify SomeColumns with fieldname as x etc.
    // That said, I did create a ticket for us to handle those that do, so you don't have to specify 'as' :-)
    val columnsForCamelCaseAlias = SomeColumns("messageId" as "messageId", "messageContent" as "messageContent", "timestamp" as "timestamp")
    vsStream.saveToCassandra("fake_data","messages", columnsForCamelCaseAlias)
    vsStream.saveToCassandra("fake_data","latest_message", columnsForCamelCaseAlias)

    vsStream.print

  }
}
