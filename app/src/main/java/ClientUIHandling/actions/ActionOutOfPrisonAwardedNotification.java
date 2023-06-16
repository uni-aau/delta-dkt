package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_NOTIFICATION;
import static ClientUIHandling.Constants.PREFIX_PRISONCARD_AWARDED_NOTIFICATION;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.R;

public class ActionOutOfPrisonAwardedNotification implements ClientActionInterface {

    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_PRISONCARD_AWARDED_NOTIFICATION, "").trim().split(";");

        Toast.makeText(activity.getApplicationContext(), LanguageHandler.formatText(activity.getString(R.string.outOfPrisonCardAwarded_notification), new Object[]{args[0]}), Toast.LENGTH_SHORT).show();
    }
}


