package ServerLogic.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;


import android.util.Log;

import java.util.List;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestRemoveUserFromList implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Remove_User_From_UserList", "Remove User from UserList Request received. Parameter: " + parameters);
        removeUserFromList(parameters.toString());
        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_REMOVE_USER_FROM_LIST, new String[]{(String) parameters});
    }

    public void removeUserFromList (String user) {

        //TODO REMOVE IN GAME.PLAYERS
    }
}
