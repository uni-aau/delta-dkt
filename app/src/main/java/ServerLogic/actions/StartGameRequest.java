package ServerLogic.actions;

import static ClientUIHandling.Constants.GameViewActivityType;
import static ClientUIHandling.Constants.PREFIX_GAME_START;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class StartGameRequest implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server START GAME", "Start request received! Server: " + server + " parameters: " + parameters);

        String[] notificationMessage = {"Test"};
        server.broadcast(GameViewActivityType, PREFIX_GAME_START, notificationMessage);
//        server.broadcast(GameViewActivityType + ":"+ PREFIX_GAME_START + " Game started!");
        // TODO
    }
}
