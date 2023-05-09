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
    private static String fixedPart = "My Properties: ";
    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("PropertyBought",  "Received a Property bought message");
        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();
        Log.i("PropertyBought",  "Trying to process request clientmessage:"+clientMessage);
        String[] splitMessage = clientMessage.split(" ");

        int id = Integer.parseInt(splitMessage[2]);
        if(id == GameViewActivity.clientID){
            TextView myPropertiesTextView =  (TextView) activity.findViewById(R.id.textView_my_properties);
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
            String newFieldVal = fixedPart+(intVal);
            ((TextView) activity.findViewById(R.id.textView_my_properties)).setText(newFieldVal);

            //set new cashValue
            ((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), Integer.parseInt(splitMessage[4])));
        }

        //was macht der Client nachdem ein anderer spieler ein grundstück gekauft hat

    }
}
