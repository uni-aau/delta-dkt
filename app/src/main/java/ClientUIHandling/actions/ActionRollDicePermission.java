package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_PERM;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;

public class ActionRollDicePermission implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("[UI] Roll Dice Perm", "Successfully received roll dice perm action from server handler: Activity: " + activity + " Message: " + clientMessage);
        Button rollDice = activity.findViewById(R.id.button_roll_dice);

        if (Game.getPlayers().containsKey(1)) {
            rollDice.setEnabled(true);
        } else {
            rollDice.setEnabled(false);
        }
    }
}
