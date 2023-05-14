package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import ClientUIHandling.Config;
import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String prefix = parameters.toString().split(" ")[0];
        String[] args = parameters.toString().substring(prefix.length()+1).split(";");

        int nextClient;
        String tag = "[Server] Roll Dice Request";
        int oldClientId = Integer.parseInt(args[0]);
        Log.d(tag, "Received next client request - Old client: " + parameters);

        // Check if clientID is last ID in game
        int size = Game.getPlayers().size();
        if (size != 0 || Config.DEBUG) {
            if (oldClientId % size == 0) {
                nextClient = 1; // swap to first player
            } else {
                nextClient = oldClientId + 1;
            }

            String nickName = Game.getPlayers().get(nextClient).getNickname();
            Log.d("[SERVER]", "New roll dice server request - prevClientID " + oldClientId + " nextClient " + nextClient + " nickName " + nickName);

            Log.d(tag, "OldClientId = " + oldClientId + " NewClient = " + nextClient);

            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{String.valueOf(nextClient), nickName});
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);

            Game.incrementRounds(oldClientId);

        } else {
            Log.e(tag, "Error - No players available in GameView");
        }
    }
}