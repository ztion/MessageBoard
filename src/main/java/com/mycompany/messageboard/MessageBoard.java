import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class MessageBoard
{
  public static void main(String[] args)
  {
    HttpServer server;
    MessageCollection messageCollection = new MessageCollection();

    try
    {
      server = HttpServer.create(new InetSocketAddress(8080), 100);
    } 
    catch(Exception err)
    {
      System.out.println("Can't create HTTP server.");
      return;      
    }

    server.createContext("/api/message", new RequestHandler(messageCollection));
    server.start();
  }
}