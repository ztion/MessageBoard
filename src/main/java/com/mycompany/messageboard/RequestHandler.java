package com.mycompany.messageboard;

import com.sun.net.httpserver.*;
import java.net.URI;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.google.gson.Gson;

public class RequestHandler implements HttpHandler
{
  private final String messagePath = "/api/message";
  private MessageCollection messageCollection;

  public RequestHandler(MessageCollection msgCollection)
  {
    messageCollection = msgCollection;
  }

  private void returnStatusCode(HttpExchange exchange, int code)
  {
    try {
      exchange.sendResponseHeaders(code, -1);
    }
    catch (Exception e)
    {
      return;
    }
  }

  private void returnInvalid(HttpExchange exchange)
  {
    returnStatusCode(exchange, 500);
    exchange.close();
  }

  private void returnNotFound(HttpExchange exchange)
  {
    returnStatusCode(exchange, 404);
  }

  private String readRequestBody(InputStream body)
  {
    char[] readBuffer = new char[64];
    StringBuffer returnString = new StringBuffer("");
    InputStreamReader reader = new InputStreamReader(body);

    try {
      while (reader.read(readBuffer) != -1)
      {
        System.out.println(readBuffer);
        returnString.append(readBuffer);
      }
    }
    catch (Exception e)
    {
      return null;
    }

    return returnString.toString();
  }

  private boolean sendResponse(HttpExchange exchange, String response, int code)
  {
    
    OutputStreamWriter writer;
    try
    {
      exchange.sendResponseHeaders(code, response.length());
    }
    catch (Exception ignore)
    {

    }

    writer = new OutputStreamWriter(exchange.getResponseBody());
    try
    {
      writer.write(response);
      writer.close();
    }
    catch (Exception ignore)
    {

    }

    return true;
  }

  private String parseIdentifier(String path)
  {
    String identifier;
    
    try
    {
      identifier = path.substring(messagePath.length() + 1);
    }
    catch (Exception e)
    {
      return "";
    }

    /* Only allow integers as identifiers */
    if (identifier.contains("/"))
    {
      return null;
    }

    return identifier;
  }

  private void handleGET(HttpExchange exchange)
  {
    URI uri = exchange.getRequestURI();
    String identifier = parseIdentifier(uri.getPath());
    String jsonOutput = null;

    System.out.println("Got request to: " + uri.getPath());

    Gson gson = new Gson();

    if (identifier == null)
    {
      returnInvalid(exchange);
      return;
    }

    if (identifier.length() == 0)
    {
      System.out.println("Path is 0");
      MessageList messageList = messageCollection.getMessageIds();

      try
      {
        jsonOutput = gson.toJson(messageList);
      }
      catch (Exception e)
      {
        System.out.println(e);
      }
    }
    else
    {
      Integer messageIndex = Integer.parseInt(identifier);
      Message message = messageCollection.getMessage(messageIndex);

      if (message == null)
      {
        returnNotFound(exchange);
        return;
      }

      jsonOutput = gson.toJson(message);

    }

    sendResponse(exchange, jsonOutput, 200);

    exchange.close();
  }

  private void handlePOST(HttpExchange exchange)
  {
    URI uri = exchange.getRequestURI();
    String path = uri.getPath();
    String readBody;
    Message newMessage = null;
    InputStream body;
    Gson gson = new Gson();


    if (path.length() > messagePath.length())
    {
      returnInvalid(exchange);
      return;
    }

    body = exchange.getRequestBody();
    readBody = readRequestBody(body);

    readBody = readBody.trim();
    try {
      newMessage = gson.fromJson(readBody, Message.class);
    }
    catch (Exception e)
    {
      System.out.println("Got error" + e);
      returnInvalid(exchange);
      return;
    }

    messageCollection.storeMessage(newMessage);

    String jsonResponse = gson.toJson(newMessage);

    sendResponse(exchange, jsonResponse, 200);

    System.out.println("Got request to: " + path);
  }

  public void handle(HttpExchange exchange)
  {
    String method = exchange.getRequestMethod().toUpperCase();

    switch (method)
    {
      case "GET":
        handleGET(exchange);
        break;
      case "POST":
        handlePOST(exchange);
      default:
        exchange.close();
        break;
    }
  }
}