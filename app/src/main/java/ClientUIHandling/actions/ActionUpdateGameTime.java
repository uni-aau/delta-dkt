package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Config;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionUpdateGameTime implements ClientActionInterface {
    private GameViewActivity gameViewActivity;

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
        Resources resources = gameViewActivity.getResources();

        int hours = (milliseconds / (1000 * 60 * 60)) % 24;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int seconds = (milliseconds / 1000) % 60;

        // Sets the text for the playing time ( != 1 -> pluralText)
        String hoursLocale = hours != 1 ? gameViewActivity.getString(R.string.hours_plural_text) : gameViewActivity.getString(R.string.hour_sing_text);
        String minutesLocale = minutes != 1 ? gameViewActivity.getString(R.string.minutes_plural_text) : gameViewActivity.getString(R.string.minute_sing_text);
        String secondsLocale = seconds != 1 ? gameViewActivity.getString(R.string.seconds_plural_text) : gameViewActivity.getString(R.string.second_sing_text);

        // %d %s, %d %s, %d, %s
        TextView playingTime = gameViewActivity.findViewById(R.id.textView_playing_time);
        String playingTimeTextInput = resources.getString(R.string.playing_time_text, String.valueOf(hours), hoursLocale, String.valueOf(minutes), minutesLocale, String.valueOf(seconds), secondsLocale);
        playingTime.setText(playingTimeTextInput);

        // Sets critical countdown color when threshold is reached
        int playingTimeTextColor;
        if (milliseconds < Config.CRITICAL_TIME && isTimeMode) {
            playingTimeTextColor = ContextCompat.getColor(gameViewActivity, R.color.critical_color);
        } else {
            ColorStateList textColors = playingTime.getTextColors();
            playingTimeTextColor = textColors.getDefaultColor(); // gets default xml color
        }
        playingTime.setTextColor(playingTimeTextColor);
    }
}
