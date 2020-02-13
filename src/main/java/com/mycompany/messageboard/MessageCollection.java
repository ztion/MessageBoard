import java.util.HashMap;

public class MessageCollection
{
  private int messageIndex = 0;
  private HashMap<Integer, Message> messageMap;

  public MessageCollection()
  {
    messageMap = new HashMap<Integer, Message>();
  }

  public int storeMessage(Message newMessage)
  {
    System.out.println("got: " + newMessage.getSubject() + " " + newMessage.getBody());
    messageMap.put(messageIndex, newMessage);

    return messageIndex++;
  }

  public Message getMessage(int index)
  {
    return messageMap.get(index);
  }
}