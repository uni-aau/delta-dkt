package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.GameViewActivity;

public class ActionRollDice implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_REQUEST, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        GameViewActivity gameViewActivity = (GameViewActivity) activity;

        if (GameViewActivity.clientID == clientId) {
            gameViewActivity.enableDice();
        } else {
            gameViewActivity.disableDice();
        }
    }
}