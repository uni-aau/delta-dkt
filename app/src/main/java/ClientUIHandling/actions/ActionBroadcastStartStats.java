package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionBroadcastStartStats implements ClientActionInterface {
    private final int MAX_PLAYER_VALUE = -1; // Todo muss via Lobby settings implementiert werden

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_GAME_START_STATS, "").trim().split(";"); // Holt sich Args nach dem Prefix

        Log.d("[UI] Broadcast Start Stats", "Received broadcast event: " + args[0]);

        // Sends start toast to every player
        String toastValue = String.format(activity.getString(R.string.start_broadcast_toast_text), args[0]);
        Toast.makeText(activity, toastValue, Toast.LENGTH_SHORT).show();

        // Updates player online textView
        updateGeneralInfo(activity, args[0]);
    }

    private void updateGeneralInfo(AppCompatActivity activity, String playerAmount) {
        String inputValue = String.format(activity.getString(R.string.players_online), Integer.parseInt(playerAmount), MAX_PLAYER_VALUE);
        ((TextView) activity.findViewById(R.id.textView_players_online)).setText(inputValue);
    }
}
