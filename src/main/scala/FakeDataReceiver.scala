import scala.collection.mutable._

import org.apache.spark.Logging
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.storage.StorageLevel

import scala.util.Random

class FakeDataReceiver( )
  extends Receiver[String](StorageLevel.MEMORY_AND_DISK_2) with Logging {

  System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


  def onStart() {
    new Thread("Fake Data Receiver") {

      override def run() {
        while(true){
          receive()
          Thread.sleep(10)
        }

      }
    }.start()
  }

  def onStop() {
  }

  private def receive(){
    val id = Random.nextLong()
    val message = Random.nextString(10)
    this.store(s"$id,$message")
  }
}
