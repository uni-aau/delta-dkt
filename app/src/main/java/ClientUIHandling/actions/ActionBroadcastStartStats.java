package ClientUIHandling.actions;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionBroadcastStartStats implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_GAME_START_STATS, "").trim().split(";"); // Holt sich Args nach dem Prefix

        Log.d("[UI] BroadcastStartStats", "Received broadcast event: " + args[0]);

        String toastValue = String.format(activity.getString(R.string.start_broadcast_toast_text), args[0]);
        Toast.makeText(activity, toastValue, Toast.LENGTH_SHORT).show();

        updateGeneralInfo();
    }

    private void updateGeneralInfo() {

    }
}
