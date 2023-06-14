package ClientUIHandling.actions;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.GameViewActivity;

public class ActionPropertyBuyToasts implements ClientActionInterface {
    private GameViewActivity gameViewActivity;
    private String languageString;
    private static final String TAG = "[Client] ActionPropertyBuyToasts";

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        gameViewActivity = (GameViewActivity) activity;
        languageString = splitMessage[0];
        int clientID = Integer.parseInt(splitMessage[1]);

        if (clientID == GameViewActivity.clientID) {
            Log.d(TAG, "Sending PropertyBuy toast message to " + clientID);
            sendToastToClient();
        }
    }

    /**
     * This method sends a specific toast to a client when there was an error buying the property
     */
    private void sendToastToClient() {
        int messageIdentifier = gameViewActivity.getResources().getIdentifier(languageString, "string", gameViewActivity.getPackageName()); // get resource key from identifier
        if (messageIdentifier != 0) {
            String messageIdentifierString = gameViewActivity.getResources().getString(messageIdentifier); // get string from resource key
            Toast.makeText(gameViewActivity.getApplicationContext(), messageIdentifierString, Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "Wrong message tag defined messageTag = " + languageString);
        }
    }
}
