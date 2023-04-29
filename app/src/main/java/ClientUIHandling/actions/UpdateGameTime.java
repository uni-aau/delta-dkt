package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class UpdateGameTime implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_GET_SERVER_TIME, "").trim().split(";");

        Log.d("UI Game Time", "Received update game time event: " + args[0]);

        updateGameTime(args[0], activity);
    }

    private void updateGameTime(String args, AppCompatActivity activity) {
        int miliseconds = Integer.parseInt(args);

        int hours = (miliseconds/ 3600000);
        int minutes = (miliseconds - hours * 3600000)/ 60000;
        int seconds = (miliseconds - hours * 3600000 - minutes * 60000) / 1000;

        ((TextView)activity.findViewById(R.id.textView_playing_time)).setText(hours + " " + minutes + " " + seconds); // Todo improve
    }
}
