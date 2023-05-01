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
//        setInitRollDicePlayer(activity);
    }

    // Shows for every clientId a specific player marker in the gui
    private void enablePlayers(AppCompatActivity activity, int clientId) {
        Log.d("[UI] Action", "Received client ID for player enabling: " + clientId);

        int figureIdentifier = activity.getResources().getIdentifier("player" + clientId, "id", activity.getPackageName());
        activity.findViewById(figureIdentifier).setVisibility(View.VISIBLE);
    }

    // Todo derzeit noch nicht implementierbar
/*    private void setInitRollDicePlayer(AppCompatActivity activity) {
        String playerNickname = Objects.requireNonNull(Game.getPlayers().get(1)).getNickname();
        String inputValue = String.format(activity.getString(R.string.dice_information_text), playerNickname);
        Log.d("[UI] Action", "Set init player name: Nickname: " + playerNickname);

        ((TextView) activity.findViewById(R.id.textView_dice_information)).setText(inputValue);
    }*/

/*    private void setInitRollDicePlayer(AppCompatActivity activity, int clientId) {
        String playerNickname = Game.getPlayers().get(clientId).getNickname();
        String inputValue = String.format(activity.getString(R.string.dice_information_text), playerNickname);
        Log.d("[UI] Action", "Set init player name: " + clientId + " Nickname: " + playerNickname);

        ((TextView) activity.findViewById(R.id.textView_dice_information)).setText(inputValue);
    }*/
}
