package ServerLogic.actions;


import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_PRISON;

import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.GoToPrisonField;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.PrisonField;
import network2.ServerNetworkClient;

public class ActionGoToPrison implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        {
            int id = (int) parameters;
            Player player = Game.getPlayers().get(id);

            int fieldLocation = player.getPosition().getLocation();
            Log.i("INFO", "LOCATION: "+fieldLocation);

            if(Game.getMap().getField(fieldLocation) instanceof GoToPrisonField){
                player.setGoToPrisonField(true);
                Log.i("INFO", "Du bist am YouGoToPrisonField; Server");
                int destination = 31;
                player.moveTo(destination);
                String[] args = new String[2];
                args[0] = String.valueOf(id);
                args[1] = String.valueOf(destination);
                server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, args);
                ServerActionHandler.triggerAction(PREFIX_PRISON, id);
            }
        }
    }
}