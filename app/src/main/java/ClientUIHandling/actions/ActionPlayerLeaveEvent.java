package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_SPECTATOR_LEAVE;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.MainMenuActivity;

public class ActionPlayerLeaveEvent implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientId = Integer.parseInt(splitMessage[0]);

        // Client host sends to the server to remove player from game
        if (MainMenuActivity.role) {
            Log.d("[CLIENT] ActionPlayerLeave", "Sending player leave action to server! ClientID = " + clientId);

            if (prefix.equals(PREFIX_PLAYER_SPECTATOR_LEAVE)) {
                Log.d("[CLIENT] ActionPlayerLeave", "Sending spectator leave action to server! ClientID = " + clientId);
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true", "true"});
            } else
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true"});
        }

        // make playerMarker invisible

    }
}
