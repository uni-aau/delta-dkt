package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionClientLeaveEvent implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientId = Integer.parseInt(splitMessage[0]);

        // Closes client connection and resets the game for specific user
        Log.d("[CLIENT] ActionPlayerLeave", "clientId = " + clientId + " clientIDGameView =" + GameViewActivity.clientID);
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
    }
}
