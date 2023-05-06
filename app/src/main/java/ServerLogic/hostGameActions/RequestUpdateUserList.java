package ServerLogic.hostGameActions;


import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestUpdateUserList implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Update_UserList", "Update UserList Request received");
        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_UPDATE_USER_LIST, new String[]{(String) parameters});
    }
}
