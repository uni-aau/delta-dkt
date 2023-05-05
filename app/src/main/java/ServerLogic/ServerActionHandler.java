package ServerLogic;

import static ClientUIHandling.Constants.*;

import ServerLogic.actions.ActionPayRent;
import ServerLogic.actions.GameStartStatsRequest;
import ServerLogic.actions.PlayerLost;
import ServerLogic.actions.RequestPlayerMovement;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import ServerLogic.actions.RequestRollDicePerm;
import ServerLogic.actions.RequestGameStartTime;
import ServerLogic.actions.RegisterUser;
import ServerLogic.actions.RequestPlayerInit;
import ServerLogic.actions.RequestGameStart;
import network2.ServerNetworkClient;

public class ServerActionHandler {
    public static final ArrayList<ServerActionInterface> actions;
    public static final ArrayList<String> actionPrefixes;
    public static HashMap<String, ServerActionInterface> actionMap;

    private static ServerNetworkClient server;

    private ServerActionHandler() {
        // no instantiation of class
    }

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();
        actionMap.put(PREFIX_PLAYER_PAYRENT, new ActionPayRent());
        actionMap.put(PREFIX_PLAYER_LOST, new PlayerLost());
        //TODO: Change the registration

        actions.add(new RequestGameStart());
        actionPrefixes.add(PREFIX_GAME_START);

        actions.add(new RegisterUser());
        actionPrefixes.add(PREFIX_REGISTER);

        actions.add(new RequestPlayerInit());
        actionPrefixes.add(PREFIX_INIT_PLAYERS);

        actions.add(new GameStartStatsRequest());
        actionPrefixes.add(PREFIX_GAME_START_STATS);

        //* Request movement when pressing dice
        actions.add(new RequestPlayerMovement());
        actionPrefixes.add(PREFIX_PLAYER_MOVE);

        actions.add(new RequestRollDicePerm());
        actionPrefixes.add(PREFIX_ROLL_DICE_REQUEST);

        actions.add(new RequestGameStartTime());
        actionPrefixes.add(PREFIX_GET_SERVER_TIME);
    }

    public static void triggerAction(String name, Object parameters){
        if(server == null){
            //Use a java class here to avoid not-mocked exception when a test reaches here.
           System.err.println("SERVER NOT SET");
            return;
        }
        //Still include old registration handling for legacy compatibility
        if(!actionPrefixes.contains(name)){
            if (actionMap.containsKey(name)) {
                actionMap.get(name).execute(server, parameters);
                return;
            }
            Log.e("ERROR","Server action does not exist: "+name);
            return;
        }

        actions.get(actionPrefixes.indexOf(name)).execute(server, parameters);
    }

    public static void setServer(ServerNetworkClient serverClient){
        server = serverClient;
    }

}
