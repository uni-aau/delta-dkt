package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RegisterUser implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[Server] Register User", "Register User request received! Server: " + server + " parameters: " + parameters);

        int clientId = Game.getPlayers().size() + 1; // Starts at id 1 instead of 0
        String uniqueNickName = "Player" + clientId;
        Log.d("[Server] Register User", "Debug - ClientID: " + clientId);

        Game.getPlayers().put(clientId, new Player(uniqueNickName));

        if(GameViewActivity.clientID == -1) {
            GameViewActivity.clientID = clientId; // Set gameview client ID
        }

        ServerActionHandler.triggerAction(Constants.PREFIX_GET_IP, MainMenuActivity.ip);
    }
}
