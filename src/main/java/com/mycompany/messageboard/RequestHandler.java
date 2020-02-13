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

  private void returnOK(HttpExchange exchange)
  {
    returnStatusCode(exchange, 200);
    exchange.close();
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

  private void handleGET(HttpExchange exchange)
  {
    URI uri = exchange.getRequestURI();
    String path = uri.getPath().substring(messagePath.length() + 1);

    System.out.println("Got request to: " + path);

    if (path.contains("/"))
    {
      returnInvalid(exchange);
      return;
    }

    if (path.length() == 0)
    {
      System.out.println("Path is 0");
    }
    else
    {
      Integer messageIndex = Integer.parseInt(path);
      Message message = messageCollection.getMessage(messageIndex);
      Gson gson = new Gson();
      String jsonOutput;

      if (message == null)
      {
        returnNotFound(exchange);
        return;
      }

      jsonOutput = gson.toJson(message);

      OutputStreamWriter writer;
      try
      {
        exchange.sendResponseHeaders(200, jsonOutput.length());
      }
      catch (Exception ignore)
      {

      }

      writer = new OutputStreamWriter(exchange.getResponseBody());
      try{
        writer.write(gson.toJson(message));
        writer.close();
      }
      catch (Exception ignore)
      {

      }
    }


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

    returnOK(exchange);

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