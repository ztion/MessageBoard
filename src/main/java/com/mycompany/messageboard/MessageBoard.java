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