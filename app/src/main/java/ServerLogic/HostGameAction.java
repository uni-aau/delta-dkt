package ServerLogic;

import static ClientUIHandling.Constants.MainMenuActivityType;
import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;

import android.util.Log;

import network2.ServerNetworkClient;

public class HostGameAction implements ServerActionInterface{

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {

        Log.d("SERVER_HOST", "Server started!!");
        server.broadcast(MainMenuActivityType+":"+ PREFIX_HOST_NEW_GAME);

    }
}
