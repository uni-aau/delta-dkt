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
    private String tag = "";

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

        if (mapField instanceof SpecialField && mapField.getName().equals("VermögensAbgabe")) {
            int playerCashTaxAmount = (int) (playerCashOld * Config.TAX_PERCENTAGE);
            int playerCashNew;
            if (playerCashTaxAmount > Config.MAX_TAX_AMOUNT) {
                playerCashNew = playerCashOld - Config.MAX_TAX_AMOUNT;
            } else {
                playerCashNew = playerCashOld - playerCashTaxAmount;
            }

            Log.d(tag, "Setting new player cash: OldPlayerCash = " + playerCashOld + " NewPlayerCash = " + playerCashNew + " ClientID = " + clientID);
            player.setCash(playerCashNew);

            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_START_CASH_VALUE, new String[]{String.valueOf(clientID), String.valueOf(playerCashNew)});
            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"pay_tax_activity_text", playerName, String.valueOf(Config.TAX_PERCENTAGE), String.valueOf(playerCashOld), String.valueOf(playerCashNew)});

        } else {
            Log.e(tag, "Error triggerAction RequestPayTax executed but player is not on specific field! ClientID = " + clientID);
        }
    }
}
