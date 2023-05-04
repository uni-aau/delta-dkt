package ServerLogic.actions;

import static ClientUIHandling.Constants.*;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

public class PlayerLost implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        if(parameters == null){
            Log.i("ERROR", "No parameters");
            return;
        }
        if(!(parameters instanceof Integer)) {
            Log.i("ERROR", "Wrong parameter");
            return;
        }
        int id = (int) parameters;

        Player player = Game.getPlayers().get(id);


        server.broadcast(GAMEVIEW_ACTIVITY_TYPE+":"+PREFIX_PLAYER_LOST+" "+player.getNickname()+" "+player.getId());

        for (int i = player.getProperties().size()-1; i>=0;i--) {
            player.getProperties().get(i).resetAccessories();
            player.getProperties().get(i).setOwner(null);
        }
        Game.getPlayers().remove(id);
    }
}
