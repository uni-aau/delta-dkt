package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_HAS_PRISONCARD;
import static ClientUIHandling.Constants.PREFIX_NOTIFICATION;

import android.util.Log;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.PrisonField;
import network2.ServerNetworkClient;

public class ActionPrison implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        {
            Log.i("INFO", "TRIGGERED ACTION");
            int id = (int) parameters;
            if (!Game.getPlayers().containsKey(id)) {
                Log.e("ERROR", "Wrong player id: " + id);
                return;
            }

            Player player = Game.getPlayers().get(id);

            int fieldLocation = player.getPosition().getLocation();
            System.out.println("LOCATION: "+fieldLocation);
            Log.i("INFO", "LOCATION: "+fieldLocation);
            Log.i("INFO", "Du bist im Gefängnis; Server");
            String[] args = new String[1];
            args[0] = String.valueOf(id);

            if(Game.getMap().getField(fieldLocation) instanceof PrisonField){
                if(player.getGoToPrisonField()){
                    Log.i("INFO", "Du warst davor am GoToPrisonField");
                    if(player.getYouGetOutOfPrisonCard()){
                        System.out.println("You have a YouGetOutOfPrison-Card!");
                        server.broadcast(GAMEVIEW_ACTIVITY_TYPE +":"+PREFIX_HAS_PRISONCARD + ": " + player.getId() + " has YouGetOutOfPrisonCard");
                        player.setYouGetOutOfPrisonCard(false);
                    }else{
                        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_NOTIFICATION, args);
                        player.setSuspension(3);
                    }
                    player.setGoToPrisonField(false);
                }
            }
        }
    }
}
