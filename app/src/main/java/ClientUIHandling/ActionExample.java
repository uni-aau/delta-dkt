package ClientUIHandling;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.R;

public class ActionExample implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        ((TextView)activity.findViewById(R.id.username_edittext)).setText(clientMessage);
    }
}
