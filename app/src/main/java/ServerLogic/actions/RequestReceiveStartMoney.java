package ServerLogic.actions;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestReceiveStartMoney implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int clientID = (int) parameters;
        Log.i("[Server] Start Money", "Received start money request with clientID " + clientID);

        if(!Game.getPlayers().containsKey(clientID)) {
            Log.e("[Server] Start Money", "Error no player in game view with clientID" + clientID);
            return;
        }

        Player player = Game.getPlayers().get(clientID);

    }
}
