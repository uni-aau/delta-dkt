package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;

public class ActionStartGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("Client UI Action", "Successfully received action from server handler: Activity: " + activity + " Message: " + clientMessage);
//        Intent switchToGameViewIntent = new Intent(activity);

    }
}
