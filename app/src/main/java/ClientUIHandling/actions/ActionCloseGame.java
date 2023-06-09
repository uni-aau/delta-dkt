package ClientUIHandling.actions;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;
import static delta.dkt.activities.MainMenuActivity.role;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.MainMenuActivity;

public class ActionCloseGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("[CLIENT]:Close_Game ", "Client closed");

        if (role) {
            Log.d("[CLIENT]:Close_Game ", "Server closed");
            Intent intent = new Intent(activity, MainMenuActivity.class);
            intent.putExtra(INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);

        }else{
            Intent intent = new Intent(activity, MainMenuActivity.class);
            intent.putExtra(INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);
        }

        tryCloseClientConnection();
    }

    private void tryCloseClientConnection() {
        // Closes client connection and reset the game
        try {
            ClientHandler.getClient().stopConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error while trying to close the client connection: " + e);
        }
    }
}
