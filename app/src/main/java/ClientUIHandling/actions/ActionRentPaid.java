package ClientUIHandling.actions;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionRentPaid implements ClientActionInterface {
    @SuppressLint("StringFormatInvalid")
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "RENTPAIDRECEIVED");
        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();

        String[] splitMessage = clientMessage.split(" ");

        int id = Integer.parseInt(splitMessage[2]);


        if (id == GameViewActivity.clientID) {
            LanguageHandler.updateTextElement(activity, "textView_cash","cash_text" , new Object[]{Integer.parseInt(splitMessage[4])});
        }

    }
}
