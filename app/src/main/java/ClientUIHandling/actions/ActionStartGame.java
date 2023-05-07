package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_REGISTER;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionStartGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("Client UI Action", "Successfully received action from server handler: Activity: " + activity + " Message: " + clientMessage);
        String[] split = clientMessage.split(";");

        GameViewActivity.players = Integer.parseInt(split[1]);

        // Starts game and switches to game activity
        Intent switchToGameViewIntent = new Intent(activity, GameViewActivity.class);
        activity.startActivity(switchToGameViewIntent);

        // Register players
        /*if(MainMenuActivity.role) {
            ServerActionHandler.triggerAction(PREFIX_REGISTER, 1);
        }*/
    }
}
