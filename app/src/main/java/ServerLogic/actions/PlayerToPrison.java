package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class PlayerToPrison implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        try {
            Set<Map.Entry<Integer, Player>> map =  Game.getPlayers().entrySet();

            int playerId = Integer.parseInt(parameters.toString());
            Player imprisonedPlayer=null;
            int clientId = -1;

            for(Map.Entry<Integer,Player> e : map){
                if(e.getValue().getId() == playerId) {
                  imprisonedPlayer = e.getValue();
                  clientId = e.getKey();
                }
            }

            if(!(imprisonedPlayer instanceof Player) || clientId < 0){
                throw new IllegalArgumentException();
            }


            String[] args = new String[2];
            args[0] = String.valueOf(clientId);
            args[1] = String.valueOf(imprisonedPlayer.getPosition().getLocation());


            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, args);

        }catch(Exception ex){
            Log.d("Error" , ex.getMessage());
            ex.printStackTrace();
        }

    }
}
