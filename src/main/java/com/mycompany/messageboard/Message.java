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

public class Message
{
  private String subject;
  private String body;
  private int id = 0;

  public String getSubject()
  {
    return subject;
  }

  public String getBody()
  {
    return body;
  }

  public void set_id(int newId)
  {
    id = newId;
  }

  public boolean verify()
  {
    if (subject == null)
    {
      return false;
    }

    if (body == null)
    {
      return false;
    }

    return true;
  }

  public void updateMessage(Message newValues)
  {
    String newSubject = newValues.getSubject();
    String newBody = newValues.getBody();

    if (newSubject != null)
    {
      subject = newSubject;
    }

    if (newBody != null)
    {
      body = newBody;
    }
  }
}
