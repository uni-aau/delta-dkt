package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PROPLIST_UPDATE;

import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestPlayerLost implements ServerActionInterface {
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

        server.broadcast(GAMEVIEW_ACTIVITY_TYPE+":"+PREFIX_PLAYER_LOST+" "+player.getNickname()+";"+player.getId());

        for (int i = player.getProperties().size()-1; i>=0;i--) {
            player.getProperties().get(i).resetAccessories();
            player.getProperties().get(i).setOwner(null);
        }

        Game.getPlayers().remove(id);
        ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, 1); // updates property list and removes player from all properties
    }
}
