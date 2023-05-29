package ServerLogic.actions;

import android.util.Log;

import java.io.IOException;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class GameEnd implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        //TODO: Evaluate which player wins/loses. For example: most wealth, most properties, etc.

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE,Constants.PREFIX_END_GAME, new String[]{(String)parameters});

        try {
            //Wait for the message to be sent, then close the server
            Thread.sleep(100);
            server.tearDown();
            Game.getPlayers().clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (InterruptedException e) {
            Log.w("Warning", "Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

    }
}
