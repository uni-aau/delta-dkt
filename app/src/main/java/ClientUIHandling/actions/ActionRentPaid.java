package ClientUIHandling.actions;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.MainActivity;

public class ActionRentPaid implements ClientActionInterface {
    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "RENTPAIDRECEIVED");
        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();

        String[] splitMessage = clientMessage.split(" ");

        //int id = Integer.parseInt(splitMessage[2]);



        //TODO: Check if the ID matches with this client and set the appropriate cash text field

        ((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), Integer.parseInt(splitMessage[4])));

    }
}
