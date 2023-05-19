package ClientUIHandling.actions;

import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionSetStartMoney implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(Constants.PREFIX_START_CASH_VALUE, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        int newCashValue = Integer.parseInt(args[1]);

        Log.d("[Client] Start Money", "Client start money action received - ClientID = " + clientId);

        if (GameViewActivity.clientID == clientId) {
            ((TextView) activity.findViewById(R.id.textView_cash)).setText(String.format(activity.getString(R.string.cash_text), String.valueOf(newCashValue)));
        }

    }
}
