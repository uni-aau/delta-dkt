package ServerLogic.actions;

import android.util.Log;

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

        if (!Game.getPlayers().containsKey((int) parameters)) {
            Log.e("ERROR", "The playerId provided to the BuyPropertyAction class does not exist. Id:  " + parameters.toString());
            return;
        }

        //fetch player object
        int clientId = (int) parameters;
        Player player = Game.getPlayers().get(clientId);
        int fieldLocation = player.getPosition().getLocation();
        Log.d(LOG_PROPERTY_BUY, "BuyProperty location: " + fieldLocation);
        Field field = Game.getMap().getField(fieldLocation);

        if (field instanceof Property && ((Property) field).getOwner() == null) {
            Property property = (Property) field;
            if (property.getPrice() <= player.getCash()) {
                boolean success = player.buyProperty(property.getLocation());
                if (success) {
                    ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, fieldLocation); // initializes property list
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_PROPERTYBOUGHT, new String[]{String.valueOf(clientId)});
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{"player_buy_property_activity_text", player.getNickname(), property.getName(), String.valueOf(property.getLocation()), String.valueOf(property.getPrice())});
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_SET_MONEY, new String[]{String.valueOf(clientId), String.valueOf(player.getCash())});
                }
            } else {
                Log.i(LOG_PROPERTY_BUY, "Player " + player.getNickname() + " has too less money to buy property!");
                server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_BUY_PROPERTY_TOASTS, new String[]{"toast_buy_property_too_less_cash", String.valueOf(clientId)});
            }
        } else {
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_BUY_PROPERTY_TOASTS, new String[]{"toast_buy_property_error_buying", String.valueOf(clientId)});
            Log.e(LOG_PROPERTY_BUY, "Error trying to buy plot - Invalid arguments provided!");
        }
    }
}
