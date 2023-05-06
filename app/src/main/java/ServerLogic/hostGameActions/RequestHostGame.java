package ServerLogic.hostGameActions;

import static ClientUIHandling.Constants.MAINMENU_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestHostGame implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {

        Log.d("[SERVER]:Host_Game", "Host Game Request received. UserName: " + parameters.toString());
        server.broadcast(MAINMENU_ACTIVITY_TYPE, PREFIX_HOST_NEW_GAME, new String[]{(String) parameters});


    }
}
