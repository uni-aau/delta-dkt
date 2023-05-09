package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestRegisterUser implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[Server] Register User", "Register User request received! Server: " + server + " parameters: " + parameters);
        String[] receivedParameters = (String[]) parameters;


        if(Game.getPlayers().size() > Config.MAX_CLIENTS) return; // Prevent registering > max size


        int clientId = Game.getPlayers().size() + 1; // Starts at id 1 instead of 0
        String uuid = receivedParameters[0];
        String nickname = receivedParameters[1];

        Game.getPlayers().put(clientId, new Player(nickname));

        if(GameViewActivity.clientID == -1) {
            GameViewActivity.clientID = clientId; // Set gameview client ID
        }

        server.broadcast(Constants.LOBBYVIEW_ACTIVITY_TYPE, Constants.PREFIX_REGISTER, new String[]{uuid, String.valueOf(clientId)});
//        ServerActionHandler.triggerAction(Constants.PREFIX_GET_IP, MainMenuActivity.ip);
    }
}
