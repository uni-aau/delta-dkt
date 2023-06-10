package ServerLogic.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.SpecialField;
import network2.ServerNetworkClient;

public class RequestPayTax implements ServerActionInterface {
    private static final String TAG = "[SERVER] PAY TAX";

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int clientID = (int) parameters;
        Log.i(TAG, "Received pay tax request with clientID " + clientID);

        if (!Game.getPlayers().containsKey(clientID)) {
            Log.e(TAG, "Error no player in game view with clientID" + clientID);
            return;
        }

        Player player = Game.getPlayers().get(clientID);
        int fieldLocation = player.getPosition().getLocation();
        String playerName = player.getNickname();
        Field mapField = Game.getMap().getField(fieldLocation);
        int playerCashOld = player.getCash();
        int playerCashNew = playerCashOld;

        if (mapField instanceof SpecialField) {
            if (mapField.getName().equals("VermögensAbgabe")) { // Reduce playercash with dynamic tax amount
                int playerCashTaxAmount = (int) (playerCashOld * Config.TAX_PERCENTAGE);
                playerCashNew = playerCashOld - playerCashTaxAmount;

                if (Config.MAX_TAX_AMOUNT != -1 && playerCashTaxAmount > Config.MAX_TAX_AMOUNT) { // If MAX_TAX_AMOUNT = -1 player money always gets reduced
                    playerCashNew = playerCashOld - Config.MAX_TAX_AMOUNT;
                }

                server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"pay_tax_activity_text", playerName, String.valueOf(Config.TAX_PERCENTAGE), String.valueOf(playerCashOld), String.valueOf(playerCashNew)});
            } else if (mapField.getName().equals("Steuerabgabe")) { // Reduce playercash with static tax amount
                playerCashNew = playerCashOld - Config.STATIC_TAX_AMOUNT;

                server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"pay_static_tax_activity_text", playerName, String.valueOf(Config.STATIC_TAX_AMOUNT), String.valueOf(playerCashOld), String.valueOf(playerCashNew)});
            }

            if (playerCashNew < 0) {
                Log.i(TAG, "Player has too less money (OldCash/Newcash) = " + playerCashOld + " / " + playerCashNew);
                ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(player.getId()), "false"}); // false -> loose event
            } else {
                player.setCash(playerCashNew);
                Log.d(TAG, "Setting new player cash: OldPlayerCash = " + playerCashOld + " NewPlayerCash = " + playerCashNew + " ClientID = " + clientID);

                server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientID), String.valueOf(playerCashNew)});
            }
        } else {
            Log.e(TAG, "Error triggerAction RequestPayTax executed but player is not on specific field! ClientID = " + clientID);
        }
    }
}
