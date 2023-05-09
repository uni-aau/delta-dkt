package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_RECEIVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class RollDiceReceive implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_RECEIVE, "").trim().split(" ");
        int clientId = Integer.parseInt(args[0]);

        if (MainMenuActivity.role) {
            Log.d("[UI] Roll Dice Next Player", "Successfully received roll dice request: Activity: " + activity + " ClientID: " + clientId + " ClientGameID: " + Game.getPlayers().get(clientId).getId());
            Log.d("TEST", "SERVER RECEIVED REQUEST with ID" + clientId);
            ServerActionHandler.triggerAction(PREFIX_ROLL_DICE_REQUEST, clientId);
        } else {
            Log.e("ERROR", "NON SERVER USER CALLED ROLLDICERECEIVE!");
        }
    }
}
