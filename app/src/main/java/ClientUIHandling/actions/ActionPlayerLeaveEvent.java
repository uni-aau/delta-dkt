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
    private int clientId;

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        clientId = Integer.parseInt(splitMessage[0]);

        sendPlayerLeaveRequestAsHost(prefix);
    }

    /**
     * This method sends a request as host to the server to remove the specific player from game
     */
    private void sendPlayerLeaveRequestAsHost(String prefix) {
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
