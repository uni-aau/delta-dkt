package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;

import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Config;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionPlayerInit implements ClientActionInterface {
    GameViewActivity gameViewActivity;
    String userName;
    String playerAmount;
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_INIT_PLAYERS, "").trim().split(";");
        gameViewActivity = (GameViewActivity) activity;
        userName = args[0];
        playerAmount = args[1];

        initDice();
        setInitTextViewValues(activity, userName); // TODO
    }

    // Shows for every clientId a specific player marker in the gui (todo)
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

    private void initDice() {
        Log.d("[UI] Roll Dice Perm", "Successfully received roll dice perm action from server handler: Activity: " + gameViewActivity + " ClientID: " + 1 + " Start-Username: " + userName);

        // Initial State (only client ID 1 can roll the dice)
        ((TextView) gameViewActivity.findViewById(R.id.textView_dice_information)).setText(String.format(gameViewActivity.getString(R.string.dice_information_text), userName));
        if (GameViewActivity.clientID == 1) {
            gameViewActivity.enableDice();
        } else {
            gameViewActivity.disableDice();
        }
    }

    // Sets initial textview values in GameView
    private void setInitTextViewValues(AppCompatActivity activity, String userName) {
        ((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), String.valueOf(Config.INITIAL_CASH)));
        ((TextView) activity.findViewById(R.id.textView_my_properties)).setText(String.format(activity.getString(R.string.my_properties_text), String.valueOf(0)));
        ((TextView) activity.findViewById(R.id.textView_activity)).setText(String.format(activity.getString(R.string.activity_text), activity.getString(R.string.game_started_acitivty_text)));

        String playerNameInputValue = String.format(activity.getString(R.string.playerName_info_text), MainMenuActivity.username);
        ((TextView) activity.findViewById(R.id.textView_playerName_spec)).setText(playerNameInputValue);
    }
}
