package ClientUIHandling.actions;

import ClientUIHandling.handlers.languages.LanguageHandler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;

import java.util.ArrayList;
import java.util.Arrays;

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
            ArrayList<String> modified = new ArrayList<>(Arrays.asList(args));
            modified.remove(0); //? remove the string-template name
            args = modified.toArray(new String[0]);
        } else {
            Log.e(TAG, "Wrong message tag defined messageTag = " + messageTag);

            //* cancel UI update when string-template is missing
            return;
        }

        LanguageHandler.updateTextElement(activity, "textView_activity", messageTag, args);
    }
}