package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.GameViewActivity;

public class ActionAskBuyProperty implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        GameViewActivity gameViewActivity = (GameViewActivity) activity;
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        int clientID = Integer.parseInt(splitMessage[0]);
        int position = Integer.parseInt(splitMessage[1]);
        Log.d("[Client] ActionAskBuyProperty", "Received property ask buy property action! " + clientID);

        if (clientID == GameViewActivity.clientID) {
            gameViewActivity.openBuyPropertyPopUp(position);
        }
    }
}
