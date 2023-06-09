package ClientUIHandling;

import ClientUIHandling.actions.ActionActivityBroadcast;
import ClientUIHandling.actions.ActionGameEnd;
import ClientUIHandling.actions.ActionGetIP;
import ClientUIHandling.actions.ActionMove;
import ClientUIHandling.actions.ActionPing;
import ClientUIHandling.actions.ActionPlayerLost;
import ClientUIHandling.actions.ActionPlayerPunish;
import ClientUIHandling.actions.ActionPrisonNotification;
import ClientUIHandling.actions.ActionPropertyListUpdate;
import ClientUIHandling.actions.ActionRentPaid;
import ClientUIHandling.actions.ActionInitRollDice;
import ClientUIHandling.actions.ActionServerIsFull;
import ClientUIHandling.actions.ActionSetMoney;
import ClientUIHandling.actions.ActionSuspensionNotification;
import ClientUIHandling.actions.ActionTimeoutWarning;
import ClientUIHandling.actions.ActionUpdateGameTime;

import ClientUIHandling.actions.cheating.ActionOpenCheatMenu;
import ClientUIHandling.actions.redirect.ActionSendServerRequest;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import ClientUIHandling.actions.ActionStartGame;
import ClientUIHandling.actions.ActionPlayerInit;
import ClientUIHandling.actions.ActionBuyProperty;
import ClientUIHandling.actions.ActionRollDiceReceive;
import ClientUIHandling.actions.ActionAddUserToUserList;
import ClientUIHandling.actions.ActionCloseGame;
import ClientUIHandling.actions.ActionHostGame;
import ClientUIHandling.actions.ActionRemoveUserFromUserList;
import ClientUIHandling.actions.ActionUpdateUserList;
import network2.NetworkClientConnection;

import static ClientUIHandling.Constants.PING;
import static ClientUIHandling.Constants.PREFIX_PLAYER_CHEAT_MENU;
import static ClientUIHandling.Constants.PREFIX_PLAYER_TIMEOUT_WARNING;
import static ClientUIHandling.Constants.PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT;

public class ClientHandler extends Handler {


    public static final ArrayList<ClientActionInterface> actions = new ArrayList<>();
    public static final ArrayList<String> actionPrefixes = new ArrayList<>();

    private final AppCompatActivity UIActivity;
    private static final HashMap<String, ClientActionInterface> actionMap =  new HashMap<>();

    private static NetworkClientConnection client;

    static{
        actionMap.put(Constants.PREFIX_PLAYER_RENTPAID, new ActionRentPaid());
        actionMap.put(Constants.PREFIX_PLAYER_LOST, new ActionPlayerLost());
        actionMap.put(Constants.PREFIX_GAME_START, new ActionStartGame());
        actionMap.put(Constants.PREFIX_ROLL_DICE_REQUEST, new ActionInitRollDice());
        actionMap.put(Constants.PREFIX_INIT_PLAYERS, new ActionPlayerInit());
        actionMap.put(Constants.PREFIX_GET_SERVER_TIME, new ActionUpdateGameTime());
        actionMap.put(Constants.PREFIX_PLAYER_MOVE, new ActionMove());
        actionMap.put(Constants.PREFIX_GET_IP, new ActionGetIP());
        actionMap.put(Constants.PREFIX_ROLL_DICE_RECEIVE, new ActionRollDiceReceive());
        actionMap.put(Constants.PREFIX_PLAYER_PROPERTYBOUGHT, new ActionBuyProperty());
        actionMap.put(Constants.PREFIX_ACTIVITY_BROADCAST, new ActionActivityBroadcast());
        actionMap.put(Constants.PREFIX_END_GAME, new ActionGameEnd());
        actionMap.put(Constants.PREFIX_SET_MONEY, new ActionSetMoney());
        actionMap.put(Constants.PREFIX_PLAYER_CHEATED, new ActionPlayerPunish());

        actionMap.put(Constants.PREFIX_NOTIFICATION, new ActionPrisonNotification());
        actionMap.put(Constants.PREFIX_PROPLIST_UPDATE, new ActionPropertyListUpdate());
        actionMap.put(Constants.PREFIX_SERVER_FULL, new ActionServerIsFull());
        actionMap.put(Constants.PREFIX_SUSPENSION_COUNT, new ActionSuspensionNotification());

        actionMap.put(PREFIX_PLAYER_CHEAT_MENU, new ActionOpenCheatMenu());
        actionMap.put(PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT, new ActionSendServerRequest());

        actionMap.put(PREFIX_PLAYER_TIMEOUT_WARNING, new ActionTimeoutWarning());

        actionMap.put(PING, new ActionPing());


        actions.add(new ActionHostGame());
        actionPrefixes.add(Constants.PREFIX_HOST_NEW_GAME);

        actions.add(new ActionUpdateUserList());
        actionPrefixes.add(Constants.PREFIX_UPDATE_USER_LIST);

        actions.add(new ActionAddUserToUserList());
        actionPrefixes.add(Constants.PREFIX_ADD_USER_TO_LIST);

        actions.add(new ActionRemoveUserFromUserList());
        actionPrefixes.add(Constants.PREFIX_REMOVE_USER_FROM_LIST);

        actions.add(new ActionCloseGame());
        actionPrefixes.add(Constants.PREFIX_CLOSE_GAME);
    }

    public static void setClient(NetworkClientConnection connection){
        client = connection;
    }

    public static NetworkClientConnection getClient() {
        return client;
    }

    public static void sendMessageToServer(String activity, String prefix, String args){
        client.sendMessage(activity+":"+prefix+" "+args);
    }

    public static void sendMessageToServer(String activity, String prefix, Object[] args){
        StringBuilder message = new StringBuilder();
        for(Object element : args) {
            message.append(element);
            if(args.length-1 != Arrays.asList(args).indexOf(element)) message.append(";"); //? Splits arguments from another with ';'
        }
        ClientHandler.sendMessageToServer(activity, prefix, message.toString());
    }


    public ClientHandler(AppCompatActivity UIActivity){
        this.UIActivity = UIActivity;

    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        String message = msg.getData().get("payload").toString();
        //For compatibility with old registration
        for (int i = 0; i < actions.size(); i++) {

            if(message.startsWith(actionPrefixes.get(i))){
                actions.get(i).execute(UIActivity, message);
                return;
            }
        }

        String[] actionSplit = message.split("[: ]");
        if (actionMap.containsKey(actionSplit[0])) {
            Log.i("INFO","TRIGGERED "+actionSplit[0]);
            actionMap.get(actionSplit[0]).execute(UIActivity, message);
            return;
        }

        Log.e("ERROR",actionSplit[0] + " NOT FOUND");

    }

}
