package ServerLogic;

import static ClientUIHandling.Constants.*;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ClientUIHandling.ClientActionInterface;
import network2.ServerNetworkClient;

public class ServerActionHandler {


    public static HashMap<String, ServerActionInterface> actionMap;

    private static ServerNetworkClient server;

    static {
        actionMap = new HashMap<>();
        actionMap.put(PREFIX_PLAYER_PAYRENT, new exampleAction());
    }

    public static void triggerAction(String name, Object parameters) {
        if (server == null) {

            Log.e("ERROR", "Server not set");
            return;
        }
        if (!actionMap.containsKey(name)) {
            Log.e("ERROR", "Server action does not exist: " + name);
            return;
        }

        actionMap.get(name).execute(server, parameters);
    }

    public static void setServer(ServerNetworkClient serverClient) {
        server = serverClient;
    }

}
