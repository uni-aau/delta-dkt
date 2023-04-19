package ServerLogic;

import static ClientUIHandling.Constants.*;

import java.util.ArrayList;

import ClientUIHandling.ClientActionInterface;
import network2.ServerNetworkClient;

public class ServerActionHandler {
    public static ArrayList<ServerActionInterface> actions;
    public static ArrayList<String> actionPrefixes;

    private static ServerNetworkClient server;

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();

        actions.add(new exampleAction());
        actionPrefixes.add(PREFIX_PLAYER_PAYRENT);
    }

    public static void triggerAction(String name, Object parameters){
        if(server == null){
            System.err.println("Server not set");
            return;
        }
        if(!actionPrefixes.contains(name)){
            System.err.println("Server action does not exist: "+name);
            return;
        }

        actions.get(actionPrefixes.indexOf(name)).execute(server, parameters);
    }

    public static void setServer(ServerNetworkClient serverClient){
        server = serverClient;
    }

}
