package ServerLogic;


import static ClientUIHandling.Constants.MAINMENU_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;
import static delta.dkt.activities.LobbyViewActivity.userList;

import android.util.Log;

import network2.ServerNetworkClient;

public class RequestAddUserToUserList implements ServerActionInterface{

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Add_User_To_UserList", "Add User to UserList Request received. Parameter: " + parameters);
        addUserToUserList(parameters.toString());


        server.broadcast(MAINMENU_ACTIVITY_TYPE, PREFIX_ADD_USER_TO_LIST, new String[]{(String) parameters} );

    }

    public void addUserToUserList(String newUser){
        userList.add(newUser);
    }
}
