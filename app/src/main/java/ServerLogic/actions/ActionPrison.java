package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ACTIVITY_BROADCAST;
import static ClientUIHandling.Constants.PREFIX_HAS_PRISONCARD;
import static ClientUIHandling.Constants.PREFIX_NOTIFICATION;

import android.util.Log;

import ClientUIHandling.Config;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.PrisonField;
import network2.ServerNetworkClient;

public class ActionPrison implements ServerActionInterface {
    private static final String TAG = "[Server] ActionPrison";
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
            int id = (int) parameters;

            Player player = Game.getPlayers().get(id);

            int fieldLocation = player.getPosition().getLocation();
            Log.i(TAG, "LOCATION: "+fieldLocation);
            Log.i(TAG, "Du bist im Gefängnis; Server");
            String[] args = new String[1];
            String playerName = player.getNickname();
            args[0] = playerName;

            if(Game.getMap().getField(fieldLocation) instanceof PrisonField){
                Log.i(TAG, "Player " + playerName + " was at GoToPrisonField before");
                if(player.getYouGetOutOfPrisonCard()){
                    Log.d(TAG, "Player " + playerName + " has a getOutOfPrisonCard");
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{"player_out_of_jail_activity_text", playerName});
                    player.setYouGetOutOfPrisonCard(false);
                }else{
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_NOTIFICATION, args);
                    player.setSuspension(Config.SUSPENSION_ROUNDS);
                }
            }
        }
}
