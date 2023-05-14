package ClientUIHandling.actions;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class ActionGetIP implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] splitMessage = clientMessage.split(";");
        if(splitMessage.length >= 1) {
            System.out.println("IP: " + clientMessage);
            ((TextView) activity.findViewById(R.id.IP)).setText("ip: " + splitMessage[0]);
        }else{
            ((TextView) activity.findViewById(R.id.IP)).setText("");
        }
    }
}
