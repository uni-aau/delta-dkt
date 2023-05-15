package ClientUIHandling.actions;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionActivityBroadcast implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_ACTIVITY_BROADCAST, "").trim().split(";"); // Holt sich Args nach dem PREFIX
        String playerName = args[0];
        String activityMessage = args[1];

        ((TextView) activity.findViewById(R.id.textView_activity)).setText(String.format(activity.getString(R.string.activity_text), playerName, activityMessage));
    }
}
