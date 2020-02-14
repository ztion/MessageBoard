package com.mycompany.messageboard;

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
    newMessage.set_id(messageIndex);
    messageMap.put(messageIndex, newMessage);

    return messageIndex++;
  }

  public Message getMessage(int index)
  {
    return messageMap.get(index);
  }

  public MessageList getMessageIds()
  {
    MessageList idList = null;
    try
    {
      idList = new MessageList(messageMap.keySet().toArray(new Integer[0]));
    }
    catch (Exception e)
    {
      System.out.println(e);
      return null;
    }

    return idList;
  }
}