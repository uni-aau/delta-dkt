package ClientUIHandling;

import ClientUIHandling.actions.ActionMove;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ClientHandler extends Handler {


    public static ArrayList<ClientActionInterface> actions;
    public static ArrayList<String> actionPrefixes;

    private AppCompatActivity UIActivity;

    static{
        actions = new ArrayList<>();
        actionPrefixes = new ArrayList<>();

        actions.add(new ActionExample());
        actionPrefixes.add(Constants.PREFIX_PLAYER_MOVE);

        actions.add(new ActionRentPaid());
        actionPrefixes.add(Constants.PREFIX_PLAYER_RENTPAID);

        actions.add(new ActionMove());
        actionPrefixes.add(Constants.PREFIX_PLAYER_MOVE);
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
        for (int i = 0; i < actions.size(); i++) {

            if(message.startsWith(actionPrefixes.get(i))){
                actions.get(i).execute(UIActivity, message);
            }
        }

    }

}
