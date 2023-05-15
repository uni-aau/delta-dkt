package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionActivityBroadcast implements ClientActionInterface {
    // Todo add var for additional values
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_ACTIVITY_BROADCAST, "").trim().split(";"); // Holt sich Args nach dem PREFIX
        String messageTag = args[0]; // get message tag
        String playerName = "null";
        String activityMessageValue = "%s - Unknown message tag";
        if (args[1] != null) {
            playerName = args[1];
        }

        int activityMessageIdentifier = activity.getResources().getIdentifier(messageTag, "string", activity.getPackageName()); // get resource key from identifier
        if (activityMessageIdentifier != 0) {
            activityMessageValue = activity.getResources().getString(activityMessageIdentifier); // get string from resource key
        } else {
            Log.e("[CLIENT]", "Wrong message tag defined messageTag = " + messageTag);
        }

        String activityMessage = String.format(activityMessageValue, playerName); // sets playername

        ((TextView) activity.findViewById(R.id.textView_activity)).setText(String.format(activity.getString(R.string.activity_text), activityMessage));
    }
}