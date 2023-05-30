package ClientUIHandling.actions;

import ClientUIHandling.handlers.languages.LanguageHandler;
import ServerLogic.handlers.ParameterHandler;
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

        if(!ParameterHandler.hasValue(args, 0, Integer.class)) {
            Log.e(tag, "ClientActionMove: the clientID is invalid! => Aborting movement update!");
            return;
        }

        if(!ParameterHandler.hasValue(args, 1, Integer.class)) {
            Log.e(tag, "ClientActionMove: the destination is invalid! => Aborting movement update!");
            return;
        }

        int clientID = ParameterHandler.getValue(args, 0, Integer.class);
        int destination = ParameterHandler.getValue(args, 1, Integer.class);


        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        gameViewActivity.updatePlayerPosition(destination, clientID);

        String result = String.format("Successfully moved (id=%s) to (pos=%s)", clientID, destination);
        Log.d(tag + "-ClientSide", result);

        var snack = SnackBarHandler.createSnackbar(activity.findViewById(R.id.imageView), result, 2000, true, "#6481d5", null);
        snack.show();
    }
}
