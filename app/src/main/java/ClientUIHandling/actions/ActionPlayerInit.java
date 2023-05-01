package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class ActionPlayerInit implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_INIT_PLAYERS, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        Button rollDice = activity.findViewById(R.id.button_roll_dice);
        ImageView gameBoard = activity.findViewById(R.id.imageView);

        Log.d("[UI] Roll Dice Perm", "Successfully received roll dice perm action from server handler: Activity: " + activity + " ClientID: " + clientId);

        // Initial State (only client ID 1 can roll the dice)
        if (clientId == 1) {
            rollDice.setEnabled(true);
            gameBoard.setEnabled(true);
            rollDice.setBackgroundResource(R.drawable.host_btn_background);
        } else {
            rollDice.setEnabled(false); // prevent touch event
            gameBoard.setEnabled(false);
            rollDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
        }

        enablePlayers(activity, clientId);
    }

    // Shows for every clientId a specific player marker in the gui
    private void enablePlayers(AppCompatActivity activity, int clientId) {
        Log.d("[UI] Action", "Received client ID for player enabling: " + clientId);

        int figureIdentifier = activity.getResources().getIdentifier("player" + clientId, "id", activity.getPackageName());
        activity.findViewById(figureIdentifier).setVisibility(View.VISIBLE);
    }
}
