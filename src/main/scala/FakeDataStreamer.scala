
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.ReceiverInputDStream
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

    config.setAppName("Fake Data Stream")

    val streamContext = new StreamingContext(config, Seconds(3) )

    generateReceiverStream(streamContext)

    streamContext.start()
    streamContext.awaitTermination()
  }

  def generateReceiverStream(aContext: StreamingContext): ReceiverInputDStream[String] = {
    val fdReceiver = new FakeDataReceiver()
    val customReceiverStream = aContext.receiverStream[String](fdReceiver)

    val vsStream = customReceiverStream.filter(!_.isEmpty())
      .map(stringToFakeMessage)

    vsStream.saveToCassandra("fake_data","messages")
    vsStream.saveToCassandra("fake_data","latest_message")

    return customReceiverStream
  }

  def stringToFakeMessage(aRecord :String): FakeMessage ={
    val arr = aRecord.split(",")
    val message = new FakeMessage(arr(0),arr(1))

    return message
  }

}
