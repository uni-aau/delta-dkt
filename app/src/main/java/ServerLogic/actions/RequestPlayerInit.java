package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestPlayerInit implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_INIT_PLAYERS, new String[]{(String) parameters});
    }
}
