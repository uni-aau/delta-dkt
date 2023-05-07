package ClientUIHandling.actions;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;

public class ActionGetIP implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] splitMessage = clientMessage.split(" ");
        if(splitMessage.length == 2) {
            System.out.println("IP: " + clientMessage);
            ((TextView) activity.findViewById(R.id.IP)).setText("ip: " + splitMessage[1]);
        }else{
            ((TextView) activity.findViewById(R.id.IP)).setText("");
        }
    }
}
