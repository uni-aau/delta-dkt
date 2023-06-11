package ClientUIHandling.actions;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionBuyProperty implements ClientActionInterface {

    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");
        Log.i("PropertyBought", "Received a Property bought message");

        int id = Integer.parseInt(splitMessage[0]);

        if (id == GameViewActivity.clientID) {
            TextView myPropertiesTextView = (TextView) activity.findViewById(R.id.textView_my_properties);
            // TODO recode
            String[] values = myPropertiesTextView.getText().toString().split(": ");

            //try to parse value into something of type int , otherwhise set 0
            int intVal;
            try {
                intVal = Integer.parseInt(values[1]);
                // use the integer value here
            } catch (NumberFormatException e) {
                intVal = 0;
            }
            //increase value
            intVal += 1;
            String newFieldVal = "My Properties: "+intVal;
            ((TextView) activity.findViewById(R.id.textView_my_properties)).setText(newFieldVal);
        }
    }
}
