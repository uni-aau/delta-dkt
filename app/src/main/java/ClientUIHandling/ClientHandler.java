package ClientUIHandling;

import ClientUIHandling.actions.ActionBroadcastStartStats;
import ClientUIHandling.actions.ActionMove;
import ClientUIHandling.actions.ActionPlayerLost;
import ClientUIHandling.actions.ActionRegisterUser;
import ClientUIHandling.actions.ActionRentPaid;
import ClientUIHandling.actions.ActionRollDice;
import ClientUIHandling.actions.ActionUpdateGameTime;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import ClientUIHandling.actions.ActionStartGame;
import ClientUIHandling.actions.ActionPlayerInit;

public class ClientHandler extends Handler {


    public static ArrayList<ClientActionInterface> actions;
    public static ArrayList<String> actionPrefixes;

    private AppCompatActivity UIActivity;
    private static HashMap<String, ClientActionInterface> actionMap;

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();

        actionMap.put(Constants.PREFIX_PLAYER_RENTPAID, new ActionRentPaid());
        actionMap.put(Constants.PREFIX_PLAYER_LOST, new ActionPlayerLost());
        actionMap.put(Constants.PREFIX_GAME_START, new ActionStartGame());
        actionMap.put(Constants.PREFIX_ROLL_DICE_REQUEST, new ActionRollDice());
        actionMap.put(Constants.PREFIX_INIT_PLAYERS, new ActionPlayerInit());
        actionMap.put(Constants.PREFIX_GAME_START_STATS, new ActionBroadcastStartStats());
        actionMap.put(Constants.PREFIX_GET_SERVER_TIME, new ActionUpdateGameTime());
        actionMap.put(Constants.PREFIX_PLAYER_MOVE, new ActionMove());
        actionMap.put(Constants.PREFIX_REGISTER, new ActionRegisterUser());
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
            actionMap.get(actionSplit[0]).execute(UIActivity, message);
        }

    }

}
