package ServerLogic;

import static ClientUIHandling.Constants.*;

import ServerLogic.actions.ActionPayRent;
import ServerLogic.actions.*;
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
import ServerLogic.actions.GetIp;
import ServerLogic.actions.RequestAddUserToUserList;
import ServerLogic.actions.RequestCloseGame;
import ServerLogic.actions.RequestHostGame;
import ServerLogic.actions.RequestRemoveUserFromList;
import ServerLogic.actions.RequestUpdateUserList;
import network2.ServerNetworkClient;

public class ServerActionHandler {
    public static final ArrayList<ServerActionInterface> actions;
    public static final ArrayList<String> actionPrefixes;
    public static HashMap<String, ServerActionInterface> actionMap;

    private static ServerNetworkClient server;

    public static ArrayList<String> serverUserList;

    private ServerActionHandler() {
        // no instantiation of class
    }

    static{
        serverUserList = new ArrayList<>();
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();
        actionMap.put(PREFIX_PLAYER_PAYRENT, new ActionPayRent());
        actionMap.put(PREFIX_PLAYER_LOST, new PlayerLost());
        actionMap.put(PREFIX_GAME_START, new RequestGameStart());
        actionMap.put(PREFIX_REGISTER, new RegisterUser());
        actionMap.put(PREFIX_INIT_PLAYERS, new RequestPlayerInit());
        actionMap.put(PREFIX_GAME_START_STATS, new RequestGameStartStats());
        actionMap.put(PREFIX_ROLL_DICE_REQUEST, new RequestRollDicePerm());
        actionMap.put(PREFIX_GET_SERVER_TIME, new RequestGameStartTime());
        actionMap.put(PREFIX_PLAYER_MOVE, new RequestPlayerMovement());
        actionMap.put(PREFIX_GET_IP, new GetIp());
        actionMap.put(PREFIX_PLAYER_BUYPROPERTY, new BuyPropertyAction());

        actions.add(new RequestHostGame());
        actionPrefixes.add(PREFIX_HOST_NEW_GAME);

        actions.add(new RequestAddUserToUserList());
        actionPrefixes.add(PREFIX_ADD_USER_TO_LIST);

        actions.add(new RequestRemoveUserFromList());
        actionPrefixes.add(PREFIX_REMOVE_USER_FROM_LIST);

        actions.add(new RequestUpdateUserList());
        actionPrefixes.add(PREFIX_UPDATE_USER_LIST);

        actions.add(new RequestCloseGame());
        actionPrefixes.add(PREFIX_CLOSE_GAME);

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
