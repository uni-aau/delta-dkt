package ServerLogic.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_LEAVE_LOBBY;


import android.util.Log;

import java.io.IOException;

import ServerLogic.ServerActionInterface;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestLeaveLobby implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {

        String leavingUser = (String) parameters;
        Log.d("[SERVER]:Leave_Lobby", leavingUser+" is leaving the lobby!");
        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_LEAVE_LOBBY, new String[]{(String) leavingUser});

        // server.broadcast updateuserlist

        if (MainMenuActivity.role) {
            try {
                //Wait for the message to be sent, then close the server
                Thread.sleep(100);
                server.tearDown();
                Log.d("[SERVER]_Server_closed", "");
                Game.getPlayers().clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Log.w("Warning", "Interrupted!", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        }
    }
}
