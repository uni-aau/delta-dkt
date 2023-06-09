package ClientUIHandling.actions;

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
        if (MainMenuActivity.role) {
            boolean isFromClient = Boolean.parseBoolean(parameters[1]);
            if(isFromClient) {
                Game.getPlayers().get(id).setHasReceivedPing(true);
            }
        } else {
            if (id == GameViewActivity.clientID) {
                ClientHandler.sendMessageToServer(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PING, "" + id+" "+1);
            }
        }
    }
}
