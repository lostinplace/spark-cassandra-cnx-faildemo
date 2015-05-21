package fake

import java.util.Date

/**
 * Created by cwheeler on 5/18/15.
 */
case class FakeMessage(messageId: String, messageContent: String, timestamp: Date) extends Serializable

object FakeMessage {

  def apply(record: String): FakeMessage = {
    val arr = record.split(",")
    FakeMessage(arr(0),arr(1), new Date())
  }
}