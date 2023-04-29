package ServerLogic;

import static ClientUIHandling.Constants.*;

import android.util.Log;

import java.util.ArrayList;

import ClientUIHandling.ClientActionInterface;
import network2.ServerNetworkClient;

public class ServerActionHandler {
    public static final ArrayList<ServerActionInterface> actions;
    public static final ArrayList<String> actionPrefixes;

    private static ServerNetworkClient server;

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();

        actions.add(new exampleAction());
        actionPrefixes.add(PREFIX_PLAYER_PAYRENT);

        actions.add(new exampleAction());
        actionPrefixes.add(PREFIX_HOST_NEW_GAME);

    }

    public static void triggerAction(String name, Object parameters){
        if(server == null){

            Log.e("ERROR","Server not set");
            return;
        }
        if(!actionPrefixes.contains(name)){
            Log.e("ERROR","Server action does not exist: "+name);
            return;
        }

        actions.get(actionPrefixes.indexOf(name)).execute(server, parameters);
    }

    public static void setServer(ServerNetworkClient serverClient){
        server = serverClient;
    }

}
