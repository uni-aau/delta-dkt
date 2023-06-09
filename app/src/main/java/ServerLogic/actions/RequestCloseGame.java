package ServerLogic.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_CLOSE_GAME;

import android.util.Log;

import java.io.IOException;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestCloseGame implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:CLOSE_Game", "Close Game Request received");

        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_CLOSE_GAME, new String[]{(String) parameters});
        tryToCloseServer(server);
    }

    private void tryToCloseServer(ServerNetworkClient server) {
        try {
            server.tearDown();
            Log.d("[Server] Close Game", "Server was closed");
            Game.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
