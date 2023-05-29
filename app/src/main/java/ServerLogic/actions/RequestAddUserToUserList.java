package ServerLogic.actions;


import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;


import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestAddUserToUserList implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Add_User_To_UserList", "Add User to UserList Request received. Parameter: " + parameters);
        Object[] args = (Object[]) parameters;
        addUserToUserList((String)args[0],(int)args[1]);


        ServerActionHandler.triggerAction(PREFIX_UPDATE_USER_LIST, null);

    }

    public void addUserToUserList(String user, int id) {
        Game.getPlayers().get(id).setNickname(user);
        Log.d("[SERVER]:Add_User_To_UserList", user+" "+id);
    }
}
