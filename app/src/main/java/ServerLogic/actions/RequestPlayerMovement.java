package ServerLogic.actions;

import ServerLogic.ServerActionInterface;
import android.util.Log;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

import static ClientUIHandling.Constants.*;

public class RequestPlayerMovement implements ServerActionInterface {
    @Override
    public void execute (ServerNetworkClient server, Object parameters) {
        Log.d("Movement-"+this.getClass().getSimpleName(), "processing movement request");

        server.broadcast(MainActivityType + ":" + PREFIX_PLAYER_MOVE + " 6");
    }
}
