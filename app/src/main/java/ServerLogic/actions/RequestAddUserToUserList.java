package ServerLogic.actions;


import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;
import static ServerLogic.ServerActionHandler.serverUserList;

import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestAddUserToUserList implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Add_User_To_UserList", "Add User to UserList Request received. Parameter: " + parameters);
        addUserToUserList(parameters.toString());
        Log.i("INFO","SERVERLIST HAS SIZE" + serverUserList.size());

        ServerActionHandler.triggerAction(PREFIX_UPDATE_USER_LIST, serverUserList);
        // server.broadcast(MAINMENU_ACTIVITY_TYPE, PREFIX_ADD_USER_TO_LIST, new String[]{(String) parameters});
    }

    public void addUserToUserList(String user) {
        serverUserList.add(user);
    }
}
