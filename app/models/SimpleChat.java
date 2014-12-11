package models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.mvc.WebSocket;

public class SimpleChat {
  private static List<WebSocket.Out<JsonNode>> connections = new ArrayList<WebSocket.Out<JsonNode>>();
  public static void start(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out) {
    connections.add(out);
    in.onMessage(new Callback<JsonNode>(){

      @Override
      public void invoke(JsonNode event) throws Throwable {
         SimpleChat.notifyAll(event);
      }
     
    });
    in.onClose(new Callback0() {
      
      @Override
      public void invoke() throws Throwable {
        //SimpleChat.notifyAll("A Connection close");
        
      }
    });
    
  }
  public static void notifyAll(JsonNode Message) {
    for(WebSocket.Out<JsonNode> out : connections){
      out.write(Message);
    }
    
  }

}
