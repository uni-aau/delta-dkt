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

        // Sets the text for the playing time ( != 1 -> pluralText)
        String hoursLocale = hours != 1 ? activity.getString(R.string.hours_plural_text) : activity.getString(R.string.hour_sing_text);
        String minutesLocale = minutes != 1 ? activity.getString(R.string.minutes_plural_text) : activity.getString(R.string.minute_sing_text);
        String secondsLocale = seconds != 1 ? activity.getString(R.string.seconds_plural_text) : activity.getString(R.string.second_sing_text);

        // %d %s, %d %s, %d, %s
        ((TextView) activity.findViewById(R.id.textView_playing_time)).setText(String.format(activity.getString(R.string.playing_time_text), hours, hoursLocale, minutes, minutesLocale, seconds, secondsLocale));
    }
}
