package ServerLogic.actions;

import static ClientUIHandling.Constants.GameViewActivityType;
import static ClientUIHandling.Constants.LobbyViewActivityType;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_PERM;

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

        int size = Game.getPlayers().size();
        System.out.println("Debug - Gamesize: " + size);
        Game.getPlayers().put(size + 1, new Player(uuid));

        // Todo start toast + update online players + button enabling
    }
}
