package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionPlayerPunish implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "PUNISHRECEIVED");


        String[] splitMessage = clientMessage.split(" ");

        for (int i = 0; i < splitMessage.length; i++) {
            System.out.println(splitMessage[i]);
        }

        int cheater = Integer.parseInt(splitMessage[0]);
        int id = Integer.parseInt(splitMessage[3]);
        String name = splitMessage[1];
        if(cheater == 1) {
            Toast.makeText(activity, name + " has cheated!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity, name + " has wrongfully reported someone!", Toast.LENGTH_LONG).show();
        }

        if (id == GameViewActivity.clientID) {

            ((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), Integer.parseInt(splitMessage[5])));
            //TODO ONCE MERGE WITH ANJA IS COMPLETED: LanguageHandler.updateTextElement(activity, "textView_cash", R.string.cash_text , new Object[]{Integer.parseInt(splitMessage[5])});
        }
    }
}
