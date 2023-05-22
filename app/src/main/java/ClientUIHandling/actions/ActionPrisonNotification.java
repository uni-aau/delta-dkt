package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_GO_TO_PRISON_FIELD;
import static ClientUIHandling.Constants.PREFIX_NOTIFICATION;
//import static ClientUIHandling.Constants.PREFIX_MOVE_PLAYER_TO_PRISON;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import ClientUIHandling.handlers.notifications.SnackBarHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionPrisonNotification implements ClientActionInterface {

    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_NOTIFICATION, "").trim().split(";");

        Toast.makeText(activity.getApplicationContext(), LanguageHandler.formatText("notify_inprisonment", new Object[]{args[0]}), Toast.LENGTH_SHORT);

    }
}


