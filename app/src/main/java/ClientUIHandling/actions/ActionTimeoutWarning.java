package ClientUIHandling.actions;

import static android.widget.Toast.LENGTH_SHORT;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.notifications.SnackBarHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionTimeoutWarning implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        int id = Integer.parseInt(clientMessage.split(" ")[1]);
        Log.i("TIMEOUT", id+" "+GameViewActivity.clientID);

        if(id != GameViewActivity.clientID) return;
        if(!(activity instanceof GameViewActivity)) return;

        View parentLayout = activity.findViewById(R.id.imageView);
        SnackBarHandler.createSnackbar(parentLayout, "Your time is nearly up!" ,LENGTH_SHORT).show();

    }
}
