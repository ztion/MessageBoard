package com.mycompany.messageboard;

public class Message
{
  private String subject;
  private String body;

  /*public Message(String newSubject, String newBody)
  {
    subject = newSubject;
    body = newBody;
  }*/

  public String getSubject()
  {
    return subject;
  }

  public String getBody()
  {
    return body;
  }
}