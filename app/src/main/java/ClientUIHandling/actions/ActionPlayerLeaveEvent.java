package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LEAVE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_SPECTATOR_LEAVE;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionPlayerLeaveEvent implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientId = Integer.parseInt(splitMessage[0]);

        Log.i("[Client] ActionPlayerLeave", "Received player leave action! ClientID = " + clientId);

        // Client host sends to the server to remove player from game
        if (MainMenuActivity.role) {
            Log.d("[CLIENT] ActionPlayerLeave", "Sending player leave action to server! ClientID = " + clientId);

            if(prefix.equals(PREFIX_PLAYER_SPECTATOR_LEAVE)) {
                Log.d("[CLIENT] ActionPlayerLeave", "Sending spectator leave action to server! ClientID = " + clientId);
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true", "true"});
            }
            else ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true"});
        }

        // make playerMarker invisible

    }
}
