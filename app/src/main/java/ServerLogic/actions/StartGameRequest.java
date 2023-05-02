package ServerLogic.actions;

import static ClientUIHandling.Constants.LobbyViewActivityType;
import static ClientUIHandling.Constants.PREFIX_GAME_START;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class StartGameRequest implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[Server] Start Game", "Start request received! Server: " + server + " parameters: " + parameters);

        server.broadcast(LobbyViewActivityType, PREFIX_GAME_START, new String[]{(String) parameters});
    }
}
