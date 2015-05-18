import java.util.Date

/**
 * Created by cwheeler on 5/18/15.
 */
class FakeMessage(
  aMessageId: String = "",
  aMessageContent: String ="",
  aTimestamp: Date = new Date()
) extends Serializable
{
  var messageId: String = aMessageId
  var messageContent: String = aMessageContent
  var timestamp: Date = aTimestamp
}


