package ServerLogic.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_LEAVE_LOBBY;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestLeaveLobby implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:CLOSE_Game", "Close Game Request received");
        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_LEAVE_LOBBY, new String[]{(String) parameters});
    }
}
