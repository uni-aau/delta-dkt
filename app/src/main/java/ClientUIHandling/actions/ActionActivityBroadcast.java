package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionActivityBroadcast implements ClientActionInterface {
    String combinedActivityMessage = "Error formatting string";

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_ACTIVITY_BROADCAST, "").trim().split(";");
        String messageTag = args[0]; // get message tag

        int activityMessageIdentifier = activity.getResources().getIdentifier(messageTag, "string", activity.getPackageName()); // get resource key from identifier
        if (activityMessageIdentifier != 0) {
            String activityMessageValue = activity.getResources().getString(activityMessageIdentifier); // get string from resource key
            formatActivityMessage(activityMessageValue, args);
        } else {
            Log.e("[CLIENT]", "Wrong message tag defined messageTag = " + messageTag);
        }

        ((TextView) activity.findViewById(R.id.textView_activity)).setText(String.format(activity.getString(R.string.activity_text), combinedActivityMessage));
    }

    // Can add up to 3 variables in activity string
    private void formatActivityMessage(String message, String[] args) {
        int argsSize = args.length;
        switch (argsSize) {
            case 1:
                combinedActivityMessage = String.format(message);
                break;
            case 2:
                combinedActivityMessage = String.format(message, args[1]);
                break;
            case 3:
                combinedActivityMessage = String.format(message, args[1], args[2]);
                break;
            case 4:
                combinedActivityMessage = String.format(message, args[1], args[2], args[3]);
            default:
                Log.e("[Client] ActivityBroadcast", "Error, too much args!");
        }
    }
}