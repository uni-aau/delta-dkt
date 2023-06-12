package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_SUSPENSION_COUNT;

import android.annotation.SuppressLint;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.R;

public class ActionSuspensionNotification implements ClientActionInterface {

    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_SUSPENSION_COUNT, "").trim().split(";");

        Toast.makeText(activity.getApplicationContext(), LanguageHandler.formatText(activity.getString(R.string.suspension_count), new Object[]{args[0]}), Toast.LENGTH_SHORT).show();
    }
}
