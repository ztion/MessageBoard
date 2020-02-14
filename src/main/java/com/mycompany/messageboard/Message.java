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
}