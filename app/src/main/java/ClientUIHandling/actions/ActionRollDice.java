package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;

public class ActionRollDice implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ROLL_DICE_REQUEST, "").trim().split(";");
        int clientId = Integer.parseInt(args[0]);
        Button rollDice = activity.findViewById(R.id.button_roll_dice);
        ImageView gameBoard = activity.findViewById(R.id.imageView);

        Log.d("[UI] Roll Dice Next Player", "Successfully received roll dice switcher: Activity: " + activity + " ClientID: " + clientId + " ClientGameID: " + Game.getPlayers().get(clientId).getId());

        for(int i = 0; i < Game.getPlayers().size(); i++) {
            if(Game.getPlayers().get(i).getId() == clientId) {
                rollDice.setEnabled(true);
                gameBoard.setEnabled(true);
                rollDice.setBackgroundResource(R.drawable.host_btn_background);
            } else {
                rollDice.setEnabled(false); // prevent touch event
                gameBoard.setEnabled(false);
                rollDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
            }
        }
    }
}
