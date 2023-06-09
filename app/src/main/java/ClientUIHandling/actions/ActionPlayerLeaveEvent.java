package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;

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

        // Closes client connection and resets the game for specific user
        if (clientId == GameViewActivity.clientID) {
            try {
                ClientHandler.getClient().stopConnection();
            } catch (Exception e) {
                throw new RuntimeException("Error while trying to close the client connection: " + e);
            }

            Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);
        }

        // Client host sends to the server to remove player from game
        if (MainMenuActivity.role) {
            Log.d("[CLIENT] ActionPlayerLeave", "Sending player leave action to server! ClientID = " + clientId);
            ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(clientId), "true"});
        }

        // make playerMarker invisible

    }
}
