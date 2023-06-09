package ClientUIHandling.actions;

import ClientUIHandling.handlers.languages.LanguageHandler;
import ServerLogic.handlers.ParameterHandler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.activities.GameViewActivity;

public class ActionMove implements ClientActionInterface {
    private String tag = "Movement-" + this.getClass().getSimpleName();

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_PLAYER_MOVE, "").trim().split(";");

        if (args[0].trim().equalsIgnoreCase("error")) {
            Log.w(tag, String.format("Updating movement location failed! Server-error: %s", args[1]));
            return;
        }

        if(!ParameterHandler.hasValue(args, 0, Integer.class)) {
            Log.e(tag, "ClientActionMove: the clientID is invalid! => Aborting movement update!");
            return;
        }

        if(!ParameterHandler.hasValue(args, 1, Integer.class)) {
            Log.e(tag, "ClientActionMove: the destination is invalid! => Aborting movement update!");
            return;
        }

        if(!ParameterHandler.hasValue(args, 2, Integer.class)) {
            Log.e(tag, "ClientActionMove: the given steps is invalid! => Aborting movement update!");
            return;
        }

        if(!ParameterHandler.hasValue(args, 3, String.class)) {
            Log.e(tag, "ClientActionMove: the players-nickname is invalid! => Aborting movement update!");
            return;
        }

        int clientID = ParameterHandler.getValue(args, 0, Integer.class);
        int destination = ParameterHandler.getValue(args, 1, Integer.class);
        int steps = ParameterHandler.getValue(args, 2, Integer.class);
        String nickName = ParameterHandler.getValue(args, 3, String.class);

        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        gameViewActivity.updatePlayerPosition(destination, clientID);

        String result = String.format("Successfully moved (id=%s) to (pos=%s)", clientID, destination);
        Log.d(tag + "-ClientSide", result);

        
        //? select the corresponding template for the message based on the amount of steps
        String templateName = steps > 1 ? "movement_info_message_plural" : "movement_info_message_singular";

        LanguageHandler.updateTextElement(activity, "textView_activity", templateName, new Object[]{nickName, String.valueOf(steps), String.valueOf(destination)});
    }
}
