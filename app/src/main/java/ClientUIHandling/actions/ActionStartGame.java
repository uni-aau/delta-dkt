package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;

public class ActionStartGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("Client UI Action", "Successfully received action from server handler: Activity: " + activity + " Message: " + clientMessage);
        String[] split = clientMessage.split(";");

//        GameViewActivity.players = Integer.parseInt(split[1]);
        String uniqueID = UUID.randomUUID().toString(); // Unique ID for register command
        GameViewActivity.uuid = uniqueID;

        ServerActionHandler.triggerAction(Constants.PREFIX_REGISTER, new String[]{uniqueID});

        // Starts game and switches to game activity
        Intent switchToGameViewIntent = new Intent(activity, GameViewActivity.class);
        activity.startActivity(switchToGameViewIntent);
    }
}
