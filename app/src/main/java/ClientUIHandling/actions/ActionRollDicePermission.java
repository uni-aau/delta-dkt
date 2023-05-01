package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_PERM;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;

public class ActionRollDicePermission implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_PERM, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        Button rollDice = activity.findViewById(R.id.button_roll_dice);

        Log.d("[UI] Roll Dice Perm", "Successfully received roll dice perm action from server handler: Activity: " + activity + " ClientID: " + clientId);

        if (clientId == 1) {
            rollDice.setEnabled(true);
            rollDice.setBackgroundResource(R.drawable.host_btn_background);
        } else {
            rollDice.setEnabled(false);
            rollDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
        }

        enablePlayers(activity, clientId);
    }

    // Shows for every clientId a specific player marker in the gui
    private void enablePlayers(AppCompatActivity activity, int clientId) {
        Log.d("[UI] Action", "Received client ID for player enabling: " + clientId);

        int figureIdentifier = activity.getResources().getIdentifier("player" + clientId, "id", activity.getPackageName());
        activity.findViewById(figureIdentifier).setVisibility(View.VISIBLE); // Todo es wird trotzdem noch die alte ID sichtbar gemacht?
    }
}
