package ClientUIHandling.actions;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;

public class ActionPlayerInit implements ClientActionInterface {
    private static final String TAG = "[CLIENT] ActionPlayerInit";
    private GameViewActivity gameViewActivity;
    private String userName;
    private int playerAmount;

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        gameViewActivity = (GameViewActivity) activity;
        parseClientMessage(clientMessage);
        Log.d("DEDUBINFO", "gameViewActivity is "+gameViewActivity);
        initDice();
        setInitTextViewValues();
    }

    // Shows for every clientId a specific player marker in the gui
    private void enablePlayers(AppCompatActivity activity, int clientId) {
        Log.d("[UI] Action", "Received client ID for player enabling: " + clientId);

        if (clientId <= Config.MAX_CLIENTS) {
            for (int i = 1; i <= Game.getPlayers().size(); i++) { // Sets the markers visible for every player (specially late joins)
                int figureIdentifier = activity.getResources().getIdentifier("player" + i, "id", activity.getPackageName());
                activity.findViewById(figureIdentifier).setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("[UI] Action Error", String.format("Error - Less player markers (%d) than players (%d)!", Config.MAX_CLIENTS, clientId));
            Toast.makeText(activity, "There was an error while adding another player - Check error logs!", Toast.LENGTH_SHORT).show();
        }
    }

    private void parseClientMessage(String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_INIT_PLAYERS, "").trim().split(";");
        userName = args[0];
        playerAmount = Integer.parseInt(args[1]);
    }

    private void initDice() {
        Log.d(TAG, "Successfully received roll dice perm action: Activity: " + gameViewActivity + " ClientID: " + 1 + " Start-Username: " + userName);

        // Initial State (only client ID 1 can roll the dice)
        TextView diceInfoTextView = gameViewActivity.findViewById(R.id.textView_dice_information);
        diceInfoTextView.setText(gameViewActivity.getString(R.string.dice_information_text, userName));

        if (GameViewActivity.clientID == 1) {
            gameViewActivity.enableDice();
        } else {
            gameViewActivity.disableDice();
        }
    }

    // Sets initial textview values in GameView
    private void setInitTextViewValues() {
        Log.d(TAG, "Successfully received action to set initial values!");
        Resources resources = gameViewActivity.getResources();

        String cashText = gameViewActivity.getString(R.string.cash_text, String.valueOf(Config.INITIAL_CASH));
        ((TextView) gameViewActivity.findViewById(R.id.textView_cash)).setText(cashText);

        String myPropertiesText = gameViewActivity.getString(R.string.my_properties_text, String.valueOf(0));
        ((TextView) gameViewActivity.findViewById(R.id.textView_my_properties)).setText(myPropertiesText);

        String playersOnlineText = gameViewActivity.getString(R.string.players_online, String.valueOf(playerAmount), String.valueOf(Config.MAX_CLIENTS));
        ((TextView) gameViewActivity.findViewById(R.id.textView_players_online)).setText(playersOnlineText);

        // Sets plural/singular textview
        String playerAmountActivityTextInput = resources.getQuantityString(R.plurals.game_started_activity_text, playerAmount, playerAmount);
        String activityTextInput = resources.getString(R.string.activity_text, playerAmountActivityTextInput);
        ((TextView) gameViewActivity.findViewById(R.id.textView_activity)).setText(activityTextInput);

        String noPlayerRolledDice = gameViewActivity.getString(R.string.no_player_rolled_dice);
        ((TextView) gameViewActivity.findViewById(R.id.textView_MovementActivity)).setText(noPlayerRolledDice);
    }
}
