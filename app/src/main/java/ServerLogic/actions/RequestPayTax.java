package ServerLogic.actions;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
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

        int playerCash = player.getCash();
    }
}
