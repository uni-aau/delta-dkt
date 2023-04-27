package ClientUIHandling.actions;

import ClientUIHandling.ClientActionInterface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import delta.dkt.R;
import delta.dkt.activities.MainActivity;

public class ActionMove implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        //todo going to update the view of each player.
        Log.d("Movement", "Updating player positions! => msg:"+clientMessage);

        Toast.makeText(activity, "Going to move player", Toast.LENGTH_SHORT).show();
    }
}
