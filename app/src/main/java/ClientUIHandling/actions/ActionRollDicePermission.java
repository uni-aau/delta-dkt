package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_PERM;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;

public class ActionRollDicePermission implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("[UI] Roll Dice Perm", "Successfully received roll dice perm action from server handler: Activity: " + activity + " Message: " + clientMessage);
        Button rollDice = activity.findViewById(R.id.button_roll_dice);

        // Todo recode - does not work
        if(Game.getPlayers().containsKey(1)) {
            rollDice.setEnabled(true);
        } else {
            rollDice.setEnabled(false);
            Toast.makeText(activity, R.string.not_your_turn_text, Toast.LENGTH_SHORT).show();
        }
    }
}
