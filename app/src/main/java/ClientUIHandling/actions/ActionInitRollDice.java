package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionInitRollDice implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_REQUEST, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        String nickName = args[1];
        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        ((TextView) activity.findViewById(R.id.textView_dice_information)).setText(String.format(activity.getString(R.string.dice_information_text), nickName));

        if (GameViewActivity.clientID == clientId) {
            gameViewActivity.enableDice();
        } else {
            gameViewActivity.disableDice();
        }
    }
}