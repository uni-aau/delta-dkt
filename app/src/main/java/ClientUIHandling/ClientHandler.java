package ClientUIHandling;

import ClientUIHandling.actions.ActionActivityBroadcast;
import ClientUIHandling.actions.ActionGameEnd;
import ClientUIHandling.actions.ActionGetIP;
import ClientUIHandling.actions.ActionMove;
import ClientUIHandling.actions.ActionPlayerLost;
import ClientUIHandling.actions.ActionPlayerPunish;
import ClientUIHandling.actions.ActionPropertyListUpdate;
import ClientUIHandling.actions.ActionRentPaid;
import ClientUIHandling.actions.ActionInitRollDice;
import ClientUIHandling.actions.ActionServerIsFull;
import ClientUIHandling.actions.ActionSetMoney;
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
import static ClientUIHandling.Constants.PREFIX_PLAYER_CHEAT_MENU;
import static ClientUIHandling.Constants.PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT;

public class ClientHandler extends Handler {


    public static ArrayList<ClientActionInterface> actions;
    public static ArrayList<String> actionPrefixes;

    private AppCompatActivity UIActivity;
    private static HashMap<String, ClientActionInterface> actionMap;

    private static NetworkClientConnection client;

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();
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
        actionMap.put(PREFIX_PLAYER_CHEAT_MENU, new ActionOpenCheatMenu());
        actionMap.put(Constants.PREFIX_PROPLIST_UPDATE, new ActionPropertyListUpdate());
        actionMap.put(Constants.PREFIX_SERVER_FULL, new ActionServerIsFull());
        actionMap.put(Constants.PREFIX_HOST_NEW_GAME, new ActionHostGame());
        actionMap.put(Constants.PREFIX_UPDATE_USER_LIST, new ActionUpdateUserList());
        actionMap.put(Constants.PREFIX_ADD_USER_TO_LIST, new ActionAddUserToUserList());
        actionMap.put(Constants.PREFIX_REMOVE_USER_FROM_LIST, new ActionRemoveUserFromUserList());
        actionMap.put(Constants.PREFIX_CLOSE_GAME, new ActionCloseGame());
        actionMap.put(PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT, new ActionSendServerRequest());

    }

    public static void setClient(NetworkClientConnection connection){
        client = connection;
    }

    public static NetworkClientConnection getClient() {
        return client;
    }

    public static void sendMessageToServer(String message){
        client.sendMessage(message);
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
       /* if(msg.getData().containsKey(testType)){
            Log.d("TEST", "REACHED");
                testView.setText(msg.getData().get(testType).toString());
        }*/
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
