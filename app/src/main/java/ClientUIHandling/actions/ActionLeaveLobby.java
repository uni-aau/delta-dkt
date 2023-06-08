package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.MainMenuActivity;

public class ActionLeaveLobby implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        MainMenuActivity mainMenuActivity = new MainMenuActivity();
        //mainMenuActivity.closeClient();
        Log.d("[CLIENT]:Close_Game ", "Client closed");








        

    }
}
