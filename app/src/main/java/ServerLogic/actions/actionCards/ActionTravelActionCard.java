package ServerLogic.actions.actionCards;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;

import android.util.Log;


import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class ActionTravelActionCard implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        try {
            int[] values = (int[]) parameters;


            int playerId = (int)values[0];
            //int orignalStartPosition = (int)values[1];
            int dest = Game.getPlayerById(playerId).getPosition().getLocation();

            String[] args = new String[2];
            args[0] = String.valueOf(Game.getClientIdByPlayerId(playerId));
            args[1] = String.valueOf(dest);
            //server.broadcast(PREFIX_ACTIONCARD_TRAVEL+" "+args[0]+" "+args[1]);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, args);

        }catch(Exception ex){
            Log.d("Error" , ex.getMessage());
            ex.printStackTrace();
        }

    }
}
