package ServerLogic.actions;

import static ClientUIHandling.Config.DEBUG;
import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;
import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {


    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = parameters.toString().trim().split(";");

        int nextClient;
        String tag = "[Server] Roll Dice Request";
        int oldClientId = Integer.parseInt(args[0]);
        Log.d(tag, "Received next client request - Old client: " + parameters);

        if (DEBUG) {
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);
            return;
        }

        // Check if clientID is last ID in game
        int size = Game.getPlayers().size();
        if (size != 0) {
            if (oldClientId % size == 0) {
                nextClient = 1; // swap to first player
            } else {
                nextClient = oldClientId + 1;
            }

            //TODO: ADD ACTION HERE THAT EITHER SKIPS PLAYER IF IMPRISONED (decreases its turnCounter); USES GOOJ-Card or let him roll
            int nextCopy = oldClientId;
            int nextClientId;
            Player p;
            do {
                nextClientId = getNextClientId(nextCopy);
                p = getNextEligiblePlayer(nextClientId);
                nextCopy = nextClientId;
            }
            while (p == null);

            Log.d("[SERVER]", "New roll dice server request - prevClientID " + oldClientId + " nextClient " + nextClientId + " nickName " + p.getNickname());

            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{String.valueOf(nextClientId), p.getNickname()});
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);

            Game.incrementRounds(oldClientId);
        }
    }


    //TODO: IF WE WANT CLIENTS TO DISPLAY SOMETHING WHEN A GOOJ-CARD IS USED THEN WE NEED TO TRIGGER AN ACTION HERE
    private Player getNextEligiblePlayer(int nextClientId){
        Player p1 = Game.getPlayers().get(nextClientId);
        if(p1 ==null ) return null; //player not found returns null

        if(p1.isSuspended()){
            //TODO: if player is skipped, trigger an event to inform clients or display a visual
            p1.handlePrisonRound();
        }
        return   p1.isSuspended() ? null : p1; //player could not be freed, return null
    }

    private int getNextClientId(int currentClientId){
        int size = Game.getPlayers().size();
        if (size != 0) {
            return (currentClientId % size) + 1;
        }
        return -1;
    }

}