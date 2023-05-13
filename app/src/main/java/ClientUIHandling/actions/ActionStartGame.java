package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;

public class ActionStartGame implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.d("Client UI Action", "Successfully received action from server handler: Activity: " + activity + " Message: " + clientMessage);
        String[] split = clientMessage.split(";");

        GameViewActivity.players = Integer.parseInt(split[1]);

               // TODO
/*        System.out.println("Game " + Game.getPlayers().size());
        System.out.println("Game " + Game.getPlayers());
        System.out.println("Game " + Game.getPlayers().get(GameViewActivity.clientID));
        System.out.println("Game " + Game.getPlayers().get(GameViewActivity.clientID).getNickname());*/


        // Starts game and switches to game activity
        Intent switchToGameViewIntent = new Intent(activity, GameViewActivity.class);
        activity.startActivity(switchToGameViewIntent);
    }
}
