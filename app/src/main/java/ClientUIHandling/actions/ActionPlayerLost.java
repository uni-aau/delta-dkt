package ClientUIHandling.actions;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionPlayerLost implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        String nickname = splitMessage[0];
        int id = Integer.parseInt(splitMessage[1]);

        Log.i("[Client] ActionPlayerLost", "Received player lost action! ClientID = " + id + " Nickname = " + nickname);

        if (GameViewActivity.clientID == id) {
            TextView playerNameTextView = activity.findViewById(R.id.textView_playerName_spec);
            playerNameTextView.setVisibility(View.VISIBLE);
            playerNameTextView.setTextColor(Color.RED);
            playerNameTextView.setTypeface(null, Typeface.BOLD);
        }
    }
}
