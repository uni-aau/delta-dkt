package ClientUIHandling.actions;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainActivity;

public class ActionMove implements ClientActionInterface {
    private String tag = "Movement-" + this.getClass().getSimpleName();

    @Override
    public void execute (AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_PLAYER_MOVE, "").trim().split(";");

        if (args[0].trim().equalsIgnoreCase("error")) {
            Log.w(tag, String.format("Updating movement location failed! Server-error: %s", args[1]));
            return;
        }

        int clientID = 0, destination = 0;

        try {
            clientID = Integer.parseInt(args[0]);
            destination = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            String msg = String.format("Parsing the clientID or location has failed, thus the map cannot be updated! (ID: %s, destination: %s)", args[0], args[1]);
            Log.e(tag, msg);
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        gameViewActivity.updatePlayerPosition(destination, clientID);
        Log.d(tag, String.format("Successfully moved %s to %s", clientID, destination));
        Toast.makeText(activity, "Successfully moved!", Toast.LENGTH_SHORT).show();
    }
}
