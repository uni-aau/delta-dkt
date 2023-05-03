package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int nextClient;
        int oldClientId = (int) parameters;
        Log.d("[Server] Roll Dice Request", "Received next client request - Old client: " + parameters);

        // Check if clientID is last ID in game
        if (oldClientId % Game.getPlayers().size() == 0) {
            nextClient = 1; // swap to first player
        } else {
            nextClient = oldClientId + 1;
        }

        Log.d("[Server] Roll Dice Request", "OldClientId = " + oldClientId + " NewClient = " + nextClient);
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{String.valueOf(nextClient)});
    }
}