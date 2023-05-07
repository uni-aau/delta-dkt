package ServerLogic.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GAME_START;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestGameStart implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[Server] Start Game", "Start request received! Server: " + server + " parameters: " + parameters);

        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_GAME_START, new String[]{(String) parameters, ""+Game.getPlayers().size()});
    }
}
