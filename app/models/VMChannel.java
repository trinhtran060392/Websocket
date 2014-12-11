package models;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.WebSocket;

public class VMChannel {
  public WebSocket.Out<JsonNode> out;
  public VMChannel(WebSocket.Out<JsonNode> channel) {
   
    this.out = channel;
   }
}
