package models;

import java.util.ArrayList;
import java.util.List;


import play.libs.F.Callback;
import play.mvc.WebSocket;

public class TodoTask {
  
  /** .*/
  private static List<WebSocket.Out<String>> listTodo = new ArrayList<WebSocket.Out<String>>();
  /**
   * 
   * @param in
   * @param out
   */
  public static void start(WebSocket.In<String> in, WebSocket.Out<String> out){
   
   listTodo.add(out);
   in.onMessage(new Callback<String>() {
    
    @Override
    public void invoke(String todo) throws Throwable {
      TodoTask.iterData(todo);
      System.out.println(todo);
    }
  });
  }
  
  /**
   * 
   * @param todo
   */
  public static void iterData(String todo) {
    for(WebSocket.Out<String> data : listTodo){     
        data.write(todo);
    }
  }
  
}
