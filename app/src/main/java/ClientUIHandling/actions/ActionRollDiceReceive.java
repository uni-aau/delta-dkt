package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_RECEIVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionRollDiceReceive implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        clientMessage = clientMessage.substring(prefix.length()+1);

        String[] args = clientMessage.split(";");

        if (MainMenuActivity.role) {
            Log.d("TEST", "SERVER RECEIVED REQUEST with ID " + args[0]);
            ServerActionHandler.triggerAction(PREFIX_ROLL_DICE_REQUEST, clientMessage);
        } else {
            Log.e("ERROR", "NON SERVER USER CALLED ROLLDICERECEIVE!");
        }
    }
}
