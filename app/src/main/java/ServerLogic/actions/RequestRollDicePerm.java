package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int nextClient;
        String tag = "[Server] Roll Dice Request";
        int oldClientId = (int) parameters;
        Log.d(tag, "Received next client request - Old client: " + parameters);

        // Check if clientID is last ID in game
        int size = Game.getPlayers().size();
        nextClient = 0;
        if (size != 0) {
            nextClient = (oldClientId % size) + 1;

            String nickName = Game.getPlayers().get(nextClient).getNickname();
            Log.d("[SERVER]", "New roll dice server request - prevClientID " + oldClientId + " nextClient " + nextClient + " nickName " + nickName);

            Log.d(tag, "OldClientId = " + oldClientId + " NewClient = " + nextClient);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{String.valueOf(nextClient), nickName});
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, oldClientId);

            Game.incrementRounds(oldClientId);

        } else {
            Log.e(tag, "Error - No players available in GameView");
        }
    }



    /**
     * @param nextPlayer: the player who´s turn it is going to be next
     * @return a boolean if the player was freed from prison via card
     */
    private boolean handlePrisonCase(Player nextPlayer){
    if(nextPlayer.isSuspended()){
            //check if the player has a get-out-of-prison-card
           // if(nextPlayer.)
        }
    return true;
    }


}