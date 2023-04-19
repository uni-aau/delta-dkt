package ClientUIHandling;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.R;
import delta.dkt.activities.MainActivity;

public class ActionRentPaid implements ClientActionInterface{
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "RENTPAIDRECEIVED");
        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();

    }
}
