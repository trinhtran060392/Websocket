package controllers;

import models.*;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;

import static akka.pattern.Patterns.ask;

public class Application extends Controller {

  public static Result index() {

    return ok(views.html.index.render());
  }

  public static Result loadJS() {

    return ok(views.js.index.render());
  }

  public static play.mvc.WebSocket<JsonNode> WebSocket() {

    return new WebSocket<JsonNode>() {

      @Override
      public void onReady(play.mvc.WebSocket.In<JsonNode> in,play.mvc.WebSocket.Out<JsonNode> out) {
        try {
          System.out.println("Application");
          Await.result(ask(StatusActor.actor, new VMChannel( out), 1000), Duration.create(1, TimeUnit.SECONDS));
          } catch (Exception e) {
          Logger.debug("Can not create akka for vm status actor", e);
          }
     // SimpleChat.start(in, out);
      }

    };
  }

  

}
