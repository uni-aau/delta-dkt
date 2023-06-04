package ServerLogic.actions;


import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GET_IP;
import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestUpdateUserList implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Update_UserList", "Update UserList Request received");

        ArrayList<Player> sortedPlayers = new ArrayList<>();

        for (Player player : Game.getPlayers().values()) {
            sortedPlayers.add(player);
        }

        Collections.sort(sortedPlayers);
        ArrayList<String> playerNames = new ArrayList<>();

        for (Player player : sortedPlayers) {
            playerNames.add(player.getNickname());
        }
        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_UPDATE_USER_LIST, playerNames.toArray(new String[]{}));
        ServerActionHandler.triggerAction(PREFIX_GET_IP, MainMenuActivity.ip);
    }
}
