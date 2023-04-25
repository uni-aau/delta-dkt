package ClientUIHandling;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Handler {


    public static ArrayList<ClientActionInterface> actions;
    public static ArrayList<String> actionPrefixes;

    private AppCompatActivity UIActivity;

    private static HashMap<String, ClientActionInterface> actionMap;

    static {
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();
        actionMap = new HashMap<>();
        actionMap.put(Constants.PREFIX_PLAYER_MOVE, new ActionExample());
        actionMap.put(Constants.PREFIX_PLAYER_RENTPAID, new ActionRentPaid());


    }


    public ClientHandler(AppCompatActivity UIActivity) {
        this.UIActivity = UIActivity;

    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        String message = msg.getData().get("payload").toString();
        String[] actionSplit = message.split("[: ]");
        if (actionMap.containsKey(actionSplit[0])) {
            actionMap.get(actionSplit[0]).execute(UIActivity, message);
        }

    }

}
