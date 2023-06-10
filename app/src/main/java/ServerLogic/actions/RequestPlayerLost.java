package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ACTIVITY_BROADCAST;
import static ClientUIHandling.Constants.PREFIX_CLIENT_LEAVE_EVENT;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;
import static ClientUIHandling.Constants.PREFIX_PROPLIST_UPDATE;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestPlayerLost implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        if (parameters == null) {
            Log.i("ERROR", "No parameters");
            return;
        }
        if (!(parameters instanceof String[])) {
            Log.i("ERROR", "Wrong parameter");
            return;
        }

        String[] args = (String[]) parameters;
        int clientID = Integer.parseInt(args[0]);
        boolean leaveEvent = Boolean.parseBoolean(args[1]);
        boolean isSpectator = false;

        if (args.length >= 3) {
            isSpectator = Boolean.parseBoolean(args[2]);
        }

        Log.d("[SERVER] RequestPlayerLost", "Received player lost request! ClientID = " + clientID + " leaveEvent =" + leaveEvent + " isSpectator =" + isSpectator);

        // If player is already spectator, player will not be removed again from game
        if (!isSpectator) {
            Player player = Game.getPlayers().get(clientID);
            String nickname = player.getNickname();
            int playerAmount = Game.getPlayers().size();

            // If leaveEvent exists, player will not be moved into spectator view
            if (!leaveEvent)
                server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_LOST, new String[]{nickname, String.valueOf(player.getId())});
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{"player_lost_activity_text", nickname});

            for (int i = player.getProperties().size() - 1; i >= 0; i--) {
                player.getProperties().get(i).resetAccessories();
                player.getProperties().get(i).setOwner(null);
            }

            // Checks if the initial playerAmount > 1
            if (playerAmount > 1) {
                Game.getPlayers().remove(clientID);
                ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, 1); // updates property list and removes player from all properties
            }

            // Check if new playerAmount equals one -> Game ends
            if (Game.getPlayers().size() == 1) {
                ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "ONLY ONE PLAYER LEFT");
            }
        }

        // Trigger leave event for client to remove player from game
        if(leaveEvent) {
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_CLIENT_LEAVE_EVENT, new String[]{String.valueOf(clientID)});
        }
    }
}
