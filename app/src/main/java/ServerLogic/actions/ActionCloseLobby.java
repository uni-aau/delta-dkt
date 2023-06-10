package ServerLogic.actions;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class ActionCloseLobby implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(Constants.LOBBYVIEW_ACTIVITY_TYPE, Constants.PREFIX_CLOSE_GAME, new String[]{});
    }
}
