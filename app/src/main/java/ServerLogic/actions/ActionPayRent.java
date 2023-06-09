package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ACTIVITY_BROADCAST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PLAYER_RENTPAID;

import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

public class ActionPayRent implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.i("INFO", "TRIGGERED ACTION");
        int id = (int) parameters;
        if (!Game.getPlayers().containsKey(id)) {
            Log.e("ERROR", "Wrong player id: " + id);
            return;
        }

        Player player = Game.getPlayers().get(id);

        int fieldLocation = player.getPosition().getLocation();
        Log.i("INFO", "LOCATION: " + fieldLocation);
        if (Game.getMap().getField(fieldLocation) instanceof Property) {
            Property property = (Property) Game.getMap().getField(fieldLocation);
            Player owner = property.getOwner();

            if (owner != null && !property.getOwner().equals(player)) {
                int rent = property.calculateRent();
                if (player.getCash() < rent) {
                    ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(player.getId()), "false", "player_lost_pay_rent_activity_text"}); // false -> loose event

                } else {
                    player.payRentTo(owner, rent);
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE + ":" + PREFIX_PLAYER_RENTPAID + " " + player.getNickname() + "(id= " + player.getId() + " ) " + player.getCash() + " " + owner.getNickname() + "(id= " + owner.getId() + " ) " + owner.getCash());
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{"player_pay_rent_activity_text", player.getNickname(), String.valueOf(rent), property.getName(), owner.getNickname()});
                }

            }
        }
    }
}
