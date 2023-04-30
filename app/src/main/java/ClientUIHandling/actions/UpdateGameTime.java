package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class UpdateGameTime implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_GET_SERVER_TIME, "").trim().split(";"); // Holt sich Args nach dem Prefix

        Log.d("[UI] GameTime", "Received update game time event: " + args[0]);

        updateGameTime(args[0], activity);
    }

    private void updateGameTime(String args, AppCompatActivity activity) {
        int milliseconds = Integer.parseInt(args);

        int seconds = (milliseconds / 1000);
        int minutes = (milliseconds / 60000);
        int hours = (milliseconds / 3600000);

        ((TextView)activity.findViewById(R.id.textView_playing_time)).setText(String.format(activity.getString(R.string.playing_time_text), hours, "Hours", minutes, "Minutes", seconds, "Seconds")); // Todo improve
    }
}
