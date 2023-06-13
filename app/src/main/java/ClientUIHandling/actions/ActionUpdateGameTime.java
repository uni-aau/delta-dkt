package ClientUIHandling.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionUpdateGameTime implements ClientActionInterface {
    private AppCompatActivity gameViewActivity;
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_GET_SERVER_TIME, "").trim().split(";"); // Holt sich Args nach dem Prefix
        gameViewActivity = (GameViewActivity) activity;
        int time = Integer.parseInt(args[0]);
        boolean isTimeMode = Boolean.parseBoolean(args[1]);

        updateGameTime(time, isTimeMode);
    }

    /**
     * This method updates the game time in the gameview (converts milliseconds to hours, minutes and seconds)
     */
    private void updateGameTime(int milliseconds, boolean isTimeMode) {
        int hours = (milliseconds / (1000 * 60 * 60)) % 24;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int seconds = (milliseconds / 1000) % 60;

        // Sets the text for the playing time ( != 1 -> pluralText)
        String hoursLocale = hours != 1 ? gameViewActivity.getString(R.string.hours_plural_text) : gameViewActivity.getString(R.string.hour_sing_text);
        String minutesLocale = minutes != 1 ? gameViewActivity.getString(R.string.minutes_plural_text) : gameViewActivity.getString(R.string.minute_sing_text);
        String secondsLocale = seconds != 1 ? gameViewActivity.getString(R.string.seconds_plural_text) : gameViewActivity.getString(R.string.second_sing_text);

        // %d %s, %d %s, %d, %s
        ((TextView) gameViewActivity.findViewById(R.id.textView_playing_time)).setText(String.format(gameViewActivity.getString(R.string.playing_time_text), String.valueOf(hours), hoursLocale, String.valueOf(minutes), minutesLocale, String.valueOf(seconds), secondsLocale));
    }
}
