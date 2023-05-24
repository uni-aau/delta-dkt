package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class ActionGetIP implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] splitMessage = clientMessage.split(" ");
        if(splitMessage.length == 2) {
            Log.i("INFO","IP: " + clientMessage);
            ((TextView) activity.findViewById(R.id.IP)).setText("ip: " + splitMessage[1]);
        }else{
            ((TextView) activity.findViewById(R.id.IP)).setText("");
        }
    }
}
