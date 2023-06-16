package ClientUIHandling.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.LOG_ERROR;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;
import static delta.dkt.activities.MainMenuActivity.role;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionCloseGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("[CLIENT]:Close_Game ", "Client closed");
        MainActivity.logic.removeType(GAMEVIEW_ACTIVITY_TYPE);
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
            Log.e(LOG_ERROR, "Error while trying to close the client connection: " + e);
        }

        Game.reset();
        LobbyViewActivity.userList.clear();
    }
}
