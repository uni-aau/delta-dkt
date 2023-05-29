package ClientUIHandling.actions;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.activities.GameViewActivity;

public class ActionMoneyChanged implements ClientActionInterface {
    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "ActionCard_MoneyChanged");
        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();

        String[] splitMessage = clientMessage.split("--");

        int clientId = Integer.parseInt(splitMessage[1]);
        int amount = Integer.parseInt(splitMessage[2]);

        if (clientId == GameViewActivity.clientID) {
            //OLD UI SETTER:
            //((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), splitMessage[4]));
            //NEW UI SETTER:
            LanguageHandler.updateTextElement(activity, "textView_cash","cash_text" , new Object[]{amount});
        }

    }
}
