package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;

public class UpdateGameTime implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("UI Game Time", "Received update game time event: " + clientMessage);
    }
}
