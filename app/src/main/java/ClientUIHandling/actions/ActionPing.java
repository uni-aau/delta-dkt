package ClientUIHandling.actions;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import ClientUIHandling.Constants;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionPing implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] parameters = clientMessage.split(" ")[1].split(";");
        int id = Integer.parseInt(parameters[0]);
        Log.i("PING", "RECEIVED "+clientMessage);
        if (MainMenuActivity.role) {
            int isFromClient = Integer.parseInt(parameters[1]);
            Log.i("PING", "ISFROMCLIENT "+isFromClient);
            if(isFromClient == 1) {
                Game.getPlayers().get(id).setHasReceivedPing(true);
            }
        } else {
            if (id == GameViewActivity.clientID) {
                ClientHandler.sendMessageToServer(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PING,id+";"+1);
            }
        }
    }
}
