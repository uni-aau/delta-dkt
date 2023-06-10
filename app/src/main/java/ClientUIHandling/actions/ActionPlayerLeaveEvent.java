package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_SPECTATOR_LEAVE;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.MainMenuActivity;

public class ActionPlayerLeaveEvent implements ClientActionInterface {
    private static final String TAG = "[CLIENT] ActionPlayerLeave";

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientId = Integer.parseInt(splitMessage[0]);

        // Host sends request to the server to remove specific player from game
        if (MainMenuActivity.role) {
            Log.d(TAG, "Sending player leave action to server! ClientID = " + clientId);

            if (prefix.equals(PREFIX_PLAYER_SPECTATOR_LEAVE)) {
                Log.d(TAG, "Sending spectator leave action to server! ClientID = " + clientId);
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true", "true"}); //true -> leaveevent & 2nd true -> spectator
            } else
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true"});
        }

    }
}
