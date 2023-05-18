package ClientUIHandling.actions;

import ClientUIHandling.handlers.languages.LanguageHandler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import ClientUIHandling.handlers.notifications.SnackBarHandler;
import delta.dkt.R;
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

        int clientID, destination;

        try {
            clientID = Integer.parseInt(args[0]);
            destination = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            String msg = String.format("Parsing the clientID or location has failed, thus the map cannot be updated! (ID: (%s), destination: (%s))", args[0], args[1]);
            Log.e(tag, msg);
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();

            return;
        }

        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        gameViewActivity.updatePlayerPosition(destination, clientID);

        String result = String.format("Successfully moved (id=%s) to (pos=%s)", clientID, destination);
        Log.d(tag + "-ClientSide", result);

        var snack = SnackBarHandler.createSnackbar(activity.findViewById(R.id.imageView), result, 2000, true, "#6481d5", null);
        snack.show();

        LanguageHandler.updateTextElement(activity, "textView_cash", "cash_text", new Object[]{"First", "Second", "Third", "Fourth", "Fifth", "Sixth"});
        /*if(MainMenuActivity.role) {
            ServerActionHandler.triggerAction(Constants.PREFIX_ROLL_DICE_REQUEST, clientID); // Sets the next player to roll the dice
        }else{
            ClientHandler.sendMessageToServer(Constants.GAMEVIEW_ACTIVITY_TYPE+":"+Constants.PREFIX_ROLL_DICE_REQUEST+" "+clientID);
        }*/
    }
}
