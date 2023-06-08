package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionPlayerLeaveEvent implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        String playerName = splitMessage[0];
        int clientId = Integer.parseInt(splitMessage[1]);

        Log.i("[Client] ActionPlayerLeave", "Received player leave action! ClientID = " + clientId + " Nickname = " + playerName);

        if (clientId == GameViewActivity.clientID) {
            // Closes client connection and resets the game:
            try {
                ClientHandler.getClient().stopConnection();
            } catch (Exception e) {
                throw new RuntimeException("Error while trying to close the client connection: " + e);
            }

            Game.reset();
            LobbyViewActivity.userList.clear();

            Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);
        }

        // Todo move client & gameKill in another class, add stopConnection(), make playerMarker invisible

    }
}
