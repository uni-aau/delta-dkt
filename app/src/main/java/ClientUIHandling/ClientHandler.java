package ClientUIHandling;

import ClientUIHandling.actions.ActionActivityBroadcast;
import ClientUIHandling.actions.ActionBroadcastStartStats;
import ClientUIHandling.actions.ActionGameEnd;
import ClientUIHandling.actions.ActionGetIP;
import ClientUIHandling.actions.ActionMove;
import ClientUIHandling.actions.ActionPlayerLost;
import ClientUIHandling.actions.ActionRentPaid;
import ClientUIHandling.actions.ActionInitRollDice;
import ClientUIHandling.actions.ActionUpdateGameTime;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
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
        actionMap.put(Constants.PREFIX_GAME_START_STATS, new ActionBroadcastStartStats());
        actionMap.put(Constants.PREFIX_GET_SERVER_TIME, new ActionUpdateGameTime());
        actionMap.put(Constants.PREFIX_PLAYER_MOVE, new ActionMove());
        actionMap.put(Constants.PREFIX_GET_IP, new ActionGetIP());
        actionMap.put(Constants.PREFIX_ROLL_DICE_RECEIVE, new ActionRollDiceReceive());
        actionMap.put(Constants.PREFIX_PLAYER_PROPERTYBOUGHT, new ActionBuyProperty());
        actionMap.put(Constants.PREFIX_ACTIVITY_BROADCAST, new ActionActivityBroadcast());
        actionMap.put(Constants.PREFIX_END_GAME, new ActionGameEnd());

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

    public static void sendMessageToServer(String message){
        client.sendMessage(message);
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
            System.out.println("TRIGGERED "+actionSplit[0]);
            actionMap.get(actionSplit[0]).execute(UIActivity, message);
            return;
        }

        System.err.println(actionSplit[0]+" NOT FOUND");

    }

}
