package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_BUYPROPERTY;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.MainMenuActivity;

public class ActionClientBuyProperty implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientId = Integer.parseInt(splitMessage[0]);

        // Host sends request to the server to remove specific player from game
        if (MainMenuActivity.role) {
            Log.d("[CLIENT] ActionClientBuyProperty", "Sending player buy property action to server! ClientID = " + clientId);

            ServerActionHandler.triggerAction(PREFIX_PLAYER_BUYPROPERTY, clientId);
        }
    }
}
