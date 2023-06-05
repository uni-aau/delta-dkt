package ServerLogic.actions.actionCards;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class PlayerToPrison implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        try {

            int playerId = Integer.parseInt(parameters.toString());
            int clientId = Game.getClientIdByPlayerId(playerId);
            Player imprisonedPlayer = Game.getPlayerById(playerId);

            if(imprisonedPlayer == null || clientId < 0){
                throw new IllegalArgumentException();
            }
            //now imprison the player, WE NEED TO return the player object since we change the instance
            imprisonedPlayer = imprisonedPlayer.suspendPlayerForRounds(3);
            //write this back to the gamestate
            Game.setPlayer(imprisonedPlayer);

            String[] args = new String[2];
            args[0] = String.valueOf(clientId);
            args[1] = String.valueOf(imprisonedPlayer.getPosition().getLocation());

            //TODO: Implement own action with we want to do something special in ClientLogic after player went to prison
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, args);

        }catch(Exception ex){
            Log.d("Error" , ex.getMessage());
            ex.printStackTrace();
        }

    }
}
