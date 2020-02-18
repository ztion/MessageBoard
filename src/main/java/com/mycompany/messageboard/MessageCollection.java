/* Simple REST message board.
 * Copyright (C) 2020 Kristoffer Brånemyr
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

  public boolean deleteMessage(int index)
  {
    if (messageMap.remove(index) == null)
    {
      return false;
    }

    return true;
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