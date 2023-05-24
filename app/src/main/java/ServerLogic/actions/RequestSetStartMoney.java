package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestSetStartMoney implements ServerActionInterface {
    private static final String TAG = "[SERVER] Start Money";

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int clientID = (int) parameters;
        Log.i(TAG, "Received start money request with clientID " + clientID);

        if (!Game.getPlayers().containsKey(clientID)) {
            Log.e(TAG, "Error no player in game view with clientID" + clientID);
            return;
        }

        Player player = Game.getPlayers().get(clientID);
        String playerName = player.getNickname();
        int playerCash = player.getCash();

        playerCash += Config.START_CASH;
        player.setCash(playerCash);
        Log.d(TAG, "Setting start cash of player: oldCash = " + player.getCash() + " / newCash = " + playerCash);

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientID), String.valueOf(playerCash)});
        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{"start_money_activity_text", playerName});

    }
}
