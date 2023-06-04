package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

public class ActionActivityBroadcast implements ClientActionInterface {
    private String combinedActivityMessage = "Error formatting string";
    private static final String TAG = "[CLIENT] ActivityBroadcast";

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_ACTIVITY_BROADCAST, "").trim().split(";");
        String messageTag = args[0]; // get message tag
        Log.d(TAG, "Received activity broadcast: messageTAG " + messageTag + " args size = " + args.length);

        int activityMessageIdentifier = activity.getResources().getIdentifier(messageTag, "string", activity.getPackageName()); // get resource key from identifier
        if (activityMessageIdentifier != 0) {
            String activityMessageValue = activity.getResources().getString(activityMessageIdentifier); // get string from resource key
            formatActivityMessage(activityMessageValue, args);
        } else {
            Log.e(TAG, "Wrong message tag defined messageTag = " + messageTag);
        }

        ((TextView) activity.findViewById(R.id.textView_activity)).setText(String.format(activity.getString(R.string.activity_text), combinedActivityMessage));
    }

    // Dynamically adds a specific amount of variables to a string
    private void formatActivityMessage(String message, String[] args) {
        int argsSize = args.length;
        try {
            Object[] formattedArgs = new Object[argsSize - 1];
            System.arraycopy(args, 1, formattedArgs, 0, argsSize - 1);
            combinedActivityMessage = String.format(message, formattedArgs);
        } catch (Exception e) {
            Log.e(TAG, "Error formatting activity broadcast message: " + e.getMessage());
        }
    }
}