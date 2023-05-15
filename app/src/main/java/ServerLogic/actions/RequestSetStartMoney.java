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

public class RequestSetStartMoney implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int clientID = (int) parameters;
        Log.i("[Server] Start Money", "Received start money request with clientID " + clientID);

        if (!Game.getPlayers().containsKey(clientID)) {
            Log.e("[Server] Start Money", "Error no player in game view with clientID" + clientID);
            return;
        }

        Player player = Game.getPlayers().get(clientID);
        String playerName = player.getNickname();
        int fieldLocation = player.getPosition().getLocation();
        Field mapField = Game.getMap().getField(fieldLocation);

        if (mapField instanceof SpecialField && mapField.getName().equals("Start")) {
            int playerCash = player.getCash();
            playerCash += Config.START_CASH;
            Log.d("[Server] Start Money", "Setting start cash of player: oldCash = " + player.getCash() + " / newCash = " + playerCash);
            player.setCash(playerCash);

            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_START_CASH_VALUE, new String[]{String.valueOf(clientID), String.valueOf(playerCash)});
            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"start_money_activity_text", playerName});
        }

    }
}
