package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.R;

public class ActionGetIP implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] splitMessage = clientMessage.split(" ");
        if(splitMessage.length == 2) {
            Log.i("INFO","IP: " + clientMessage);

            String formattedIpMessage = LanguageHandler.formatText(activity.getString(R.string.text_ip), new Object[]{splitMessage[1]});
            ((TextView) activity.findViewById(R.id.IP)).setText(formattedIpMessage);
        }else{
            ((TextView) activity.findViewById(R.id.IP)).setText("");
        }
    }
}
