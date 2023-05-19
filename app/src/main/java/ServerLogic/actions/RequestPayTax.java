package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.SpecialField;
import network2.ServerNetworkClient;

public class RequestPayTax implements ServerActionInterface {
    private final static String tag = "[SERVER] PAY TAX";

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int clientID = (int) parameters;
        Log.i(tag, "Received pay tax request with clientID " + clientID);

        if (!Game.getPlayers().containsKey(clientID)) {
            Log.e(tag, "Error no player in game view with clientID" + clientID);
            return;
        }

        Player player = Game.getPlayers().get(clientID);
        String playerName = player.getNickname();
        int fieldLocation = player.getPosition().getLocation();
        Field mapField = Game.getMap().getField(fieldLocation);
        int playerCashOld = player.getCash();
        int playerCashNew = playerCashOld;

        if (mapField instanceof SpecialField) {
            if (mapField.getName().equals("VermögensAbgabe")) { // Reduce playercash with dynamic tax amount
                int playerCashTaxAmount = (int) (playerCashOld * Config.TAX_PERCENTAGE);
                playerCashNew = playerCashOld - playerCashTaxAmount;

                if (Config.MAX_TAX_AMOUNT != -1) { // If MAX_TAX_AMOUNT = -1 player money always gets reduced
                    if (playerCashTaxAmount > Config.MAX_TAX_AMOUNT) {
                        playerCashNew = playerCashOld - Config.MAX_TAX_AMOUNT;
                    }
                }
                server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"pay_tax_activity_text", playerName, String.valueOf(Config.TAX_PERCENTAGE), String.valueOf(playerCashOld), String.valueOf(playerCashNew)});
            } else if (mapField.getName().equals("Steuerabgabe")) { // Reduce playercash with static tax amount
                playerCashNew = playerCashOld - Config.STATIC_TAX_AMOUNT;
                server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"pay_static_tax_activity_text", playerName, String.valueOf(Config.STATIC_TAX_AMOUNT), String.valueOf(playerCashOld), String.valueOf(playerCashNew)});
            }

            Log.d(tag, "Setting new player cash: OldPlayerCash = " + playerCashOld + " NewPlayerCash = " + playerCashNew + " ClientID = " + clientID);
            player.setCash(playerCashNew);

            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientID), String.valueOf(playerCashNew)});
        } else {
            Log.e(tag, "Error triggerAction RequestPayTax executed but player is not on specific field! ClientID = " + clientID);
        }
    }
}
