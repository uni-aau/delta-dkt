package ServerLogic.actions;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RegisterUser implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String uuid = (String) parameters;
        Log.d("Server Register User", "Register User request received! Server: " + server + " parameters: " + parameters);

        int clientId = Game.getPlayers().size() + 1; // Starts at 1 instead of 0
        System.out.println("Debug - ClientID: " + clientId);
        Game.getPlayers().put(clientId, new Player(uuid));

        GameViewActivity.clientID = clientId;

        // Todo button enabling
    }
}
