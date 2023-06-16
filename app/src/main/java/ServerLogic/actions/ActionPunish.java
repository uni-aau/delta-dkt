package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_CHEATED;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;

import android.util.Log;

import ClientUIHandling.Config;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class ActionPunish implements ServerActionInterface {
    private static final String TAG = "[Server] ActionPunish";
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        //! Parameter format: [<correctlyReported>, <playerID>]

        if(!(parameters instanceof Object[])){
            Log.e(TAG,"WRONG PARAMETERS FOR ActionPunish, expected an array!");
            return;
        }
        Object[] parameterArray = (Object[]) parameters;
        if(!(parameterArray[0] instanceof Boolean)){
            Log.e(TAG,"WRONG PARAMETERS FOR ActionPunish, expected boolean as first element!");
            return;
        }
        if(!(parameterArray[1] instanceof Integer)){
            Log.e(TAG,"WRONG PARAMETERS FOR ActionPunish, expected integer as second element!");
            return;
        }

        boolean isCheater = (boolean) parameterArray[0];
        int id = (int) parameterArray[1];
        Player player = Game.getPlayers().get(id);
        if(isCheater){
            player.setCash(player.getCash()- Config.PUNISHMENT_FOR_CHEATING);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_CHEATED+" 1;"+player.getNickname()+";"+player.getId()+";"+player.getCash());

        }else{
            player.setCash(player.getCash()- Config.PUNISHMENT_FOR_WRONG_REPORT);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_PLAYER_CHEATED+" 0;"+player.getNickname()+";"+player.getId()+";"+player.getCash());
        }

        if(player.getCash() < 0){
            ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(player.getId()), "false"}); // false -> loose event
        }
    }
}
