package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import network2.ServerNetworkClient;

import static ClientUIHandling.Constants.*;

public class RequestBuyProperty implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
            Log.d("INFO", "BuyPropertyAction");

            if (! Game.getPlayers().containsKey((int)parameters)) {
                Log.e("ERROR","The playerId provided to the BuyPropertyAction class does not exist. Id:  " + parameters.toString());
                return;
            }

            //fetch player object
            int clientId = (int) parameters;
            Player player = Game.getPlayers().get(clientId);
            int fieldLocation = player.getPosition().getLocation();
            Log.d(LOG_PROPERTY_BUY, "BuyProperty location: "+fieldLocation);
            Field field = Game.getMap().getField(fieldLocation);

            if(field instanceof Property && ((Property) field).getOwner() == null) {
                Property property = (Property) field;
                if(property.getPrice() <= player.getCash()) {
                    boolean success = player.buyProperty(property.getLocation());
                    if (success) {
                        ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, fieldLocation); // initializes property list
                        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_PROPERTYBOUGHT, new String[]{String.valueOf(player.getId())});
                        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientId), String.valueOf(player.getCash())});
                    }
                } else {
                    // server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_PROPERTYBOUGHT, new String[]{player.getNickname(), String.valueOf(player.getId()), String.valueOf(player.getCash()), property.getName(), String.valueOf(property.getLocation())});
                    // TODO GELD REICHT NICHT AUS
                }
            } else {
                Log.e(LOG_PROPERTY_BUY, "Error trying to buy plot - Invalid arguments provided!");
            }
        }
    }
