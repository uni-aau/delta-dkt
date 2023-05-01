package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_REGISTER;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;

public class ActionStartGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("Client UI Action", "Successfully received action from server handler: Activity: " + activity + " Message: " + clientMessage);
        Intent switchToGameViewIntent = new Intent(activity, GameViewActivity.class);
        activity.startActivity(switchToGameViewIntent);

        ServerActionHandler.triggerAction(PREFIX_REGISTER, 1);
    }
}
