package ClientUIHandling.actions;

import static delta.dkt.activities.MainMenuActivity.role;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.MainMenuActivity;

public class ActionCloseGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        MainMenuActivity mainMenuActivity = new MainMenuActivity();
        //mainMenuActivity.closeClient();
        Log.d("[CLIENT]:Close_Game ", "Client closed");

        if (role) {
            //mainMenuActivity.closeServer();
            Log.d("[CLIENT]:Close_Game ", "Server closed");
            Intent intent = new Intent(activity, MainMenuActivity.class);
            activity.startActivity(intent);
        }else{
            Intent intent = new Intent(activity, MainMenuActivity.class);
            activity.startActivity(intent);
        }






        

    }
}
