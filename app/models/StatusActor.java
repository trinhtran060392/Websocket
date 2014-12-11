package models;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.ats.cloudstack.CloudStackClient;
import org.ats.cloudstack.VirtualMachineAPI;
import org.ats.cloudstack.model.VirtualMachine;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Akka;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class StatusActor extends UntypedActor {

  public static ActorRef actor = Akka.system().actorOf(
      Props.create(StatusActor.class));
  final static Cancellable canceller = Akka
      .system()
      .scheduler()
      .schedule(Duration.create(100, TimeUnit.MILLISECONDS),
          Duration.create(5, SECONDS), actor, "Check",
          Akka.system().dispatcher(), null);

  Map<String, VMChannel> channels = new HashMap<String, VMChannel>();

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof VMChannel) {
      VMChannel channel = (VMChannel) message;
      System.out.println("instanceof VMChannel");
      channels.put("1", channel);
      getSender().tell("OK", getSelf());
    } 
    else if (message.equals("Check")) {
     for(VMChannel channel : channels.values()){
       CloudStackClient client = new CloudStackClient("http://172.27.4.48:8080/client/api", 
         "lclT8f6KWtKbNt-lqaBlcFg8DFnbe15tKj1ZMs098D24K1EJEo0ZP3u8jQ-e1a6cA5xlOfRLrZTPbp-I-TGrcQ", 
         "ANh9Uh4Jqjzid49g5nY63ukNtEq4rawAuQyVhvYalgnuDGbRCtBrAq0XNIEGBbXZHO9EvyXop3jAkKqqw_LJdg");
       List<VirtualMachine> vms = VirtualMachineAPI.listVirtualMachines(client, null, null, null, null, null);
    
       ArrayNode array = Json.newObject().arrayNode();
       for(VirtualMachine vm : vms){
         array.add(Json.newObject().put("ip", vm.nic[0].ipAddress ).put("name", vm.name).put("state", vm.state));
       
       }
       channel.out.write(array);
     }
    }
    else {
      unhandled(message);
    }

  }
}
