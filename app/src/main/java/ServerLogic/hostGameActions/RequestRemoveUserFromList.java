package ServerLogic.hostGameActions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.MAINMENU_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;
import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;
import static ServerLogic.ServerActionHandler.serverUserList;
import static delta.dkt.activities.LobbyViewActivity.userList;

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
        serverUserList.removeAll(List.of(user));
    }
}
