package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.file.LinkOption;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.handlers.languages.LanguageHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

import static ClientUIHandling.Constants.LOG_MAIN;

public class ActionPlayerPunish implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "PUNISHRECEIVED");
        //! ClientMessage format: "PUNISH <correctlyReported> <name> <id> <cash>"

        String prefix = clientMessage.split(" ")[0];
        String[] splitMessage = clientMessage.replace(prefix, "").trim().split(";");

        for (int i = 0; i < splitMessage.length; i++) {
            Log.i("INFO", splitMessage[i]);
        }

        int cheater = Integer.parseInt(splitMessage[0]);
        int id = Integer.parseInt(splitMessage[2]);
        String name = splitMessage[1];
        if(cheater == 1) {
            Toast.makeText(activity, name + " has cheated!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity, name + " has wrongfully reported someone!", Toast.LENGTH_LONG).show();
        }

        if (id == GameViewActivity.clientID) {
            LanguageHandler.updateTextElement(activity, "textView_cash","cash_text" , new Object[]{Integer.parseInt(splitMessage[3])});
        }
    }
}
