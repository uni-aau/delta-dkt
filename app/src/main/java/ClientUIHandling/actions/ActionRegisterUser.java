package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.activities.GameViewActivity;

public class ActionRegisterUser implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_REGISTER, "").trim().split(";"); // Holt sich Args nach dem Prefix
        String uuid = args[0];
        int clientId = Integer.parseInt(args[1]);

        // Check if uuid equals with client uuid
        if (!uuid.equals(GameViewActivity.uuid)) return;

        GameViewActivity.clientID = clientId;
        Log.d("[Client] Register", "Success - Registered user with userId " + uuid + " and clientID " + clientId);
    }
}