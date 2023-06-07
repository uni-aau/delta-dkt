package ServerLogic;

import static ClientUIHandling.Constants.*;

import ServerLogic.actions.*;
import ServerLogic.actions.cheating.RequestCheatMenu;
import ServerLogic.actions.cheating.RequestReportCheater;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import network2.ServerNetworkClient;

public class ServerActionHandler {
    public static final ArrayList<ServerActionInterface> actions;
    public static final ArrayList<String> actionPrefixes;
    public static final HashMap<String, ServerActionInterface> actionMap;

    private static ServerNetworkClient server;



    private ServerActionHandler() {
        // no instantiation of class
    }

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();
        actionMap.put(PREFIX_PLAYER_PAYRENT, new ActionPayRent());
        actionMap.put(PREFIX_PLAYER_LOST, new RequestPlayerLost());
        actionMap.put(PREFIX_GAME_START, new RequestGameStart());
        actionMap.put(PREFIX_INIT_PLAYERS, new RequestPlayerInit());
        actionMap.put(PREFIX_ROLL_DICE_REQUEST, new RequestRollDicePerm());
        actionMap.put(PREFIX_GET_SERVER_TIME, new RequestGameStartTime());
        actionMap.put(PREFIX_PLAYER_MOVE, new RequestPlayerMovement());
        actionMap.put(PREFIX_GET_IP, new RequestGetIp());
        actionMap.put(PREFIX_PLAYER_BUYPROPERTY, new RequestBuyProperty());
        actionMap.put(PREFIX_END_GAME, new GameEnd());
        actionMap.put(PREFIX_PLAYER_CHEATED, new ActionPunish());
        actionMap.put(PREFIX_START_CASH_VALUE, new RequestSetStartMoney());
        actionMap.put(PREFIX_PAY_TAX, new RequestPayTax());

        actionMap.put(PREFIX_HAS_PRISONCARD, new ActionPrison());
        actionMap.put(PREFIX_GO_TO_PRISON_FIELD, new ActionGoToPrison());
        actionMap.put(PREFIX_PRISON, new ActionPrison());

        actionMap.put(PREFIX_PLAYER_CHEAT_MENU, new RequestCheatMenu());
        actionMap.put(PREFIX_PLAYER_REPORT_CHEATER, new RequestReportCheater());

        actionMap.put(PREFIX_PROPLIST_UPDATE, new RequestPropertyListUpdate());


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
            //But due to sonarcloud, we simply do not log it instead...
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
