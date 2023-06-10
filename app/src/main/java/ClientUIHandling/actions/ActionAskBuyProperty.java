package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.GameViewActivity;

public class ActionAskBuyProperty implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("[Client] ActionAskBuyProperty", "Received property ask buy property action! " + clientMessage);
        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        gameViewActivity.openBuyPropertyPopUp();
    }
}
