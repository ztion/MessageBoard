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
