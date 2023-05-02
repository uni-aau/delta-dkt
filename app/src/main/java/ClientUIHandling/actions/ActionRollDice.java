package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;

public class ActionRollDice implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_REQUEST, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        Log.d("[UI] Roll Dice Next Player", "Successfully received roll dice request: Activity: " + activity + " ClientID: " + clientId + " ClientGameID: " + Game.getPlayers().get(clientId).getId());

        if (GameViewActivity.clientID == clientId) {
            gameViewActivity.enableDice();
        } else {
            gameViewActivity.disableDice();
        }
    }
}