package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestRegisterUser implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[Server] Register User", "Register User request received! Server: " + server + " parameters: " + parameters);

        if(Game.getPlayers().size() > Config.MAX_CLIENTS) return; // Prevent registering > max size

        int clientId = Game.getPlayers().size() + 1; // Starts at id 1 instead of 0
        String nickname = "Player" + clientId; // Todo change to lobby names in next sprint
        String uuid = (String) parameters;

        Game.getPlayers().put(clientId, new Player(nickname));

        server.broadcast(Constants.LOBBYVIEW_ACTIVITY_TYPE, Constants.PREFIX_REGISTER, new String[]{uuid, String.valueOf(clientId)});

//        GameViewActivity.clientID = clientId; // Set gameview client ID
    }
}
