package ServerLogic.actions;



import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestRemoveUserFromList implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Remove_User_From_UserList", "Remove User from UserList Request received. Parameter: " + parameters);
        removeUserFromList((int)parameters);
        ServerActionHandler.triggerAction(Constants.PREFIX_UPDATE_USER_LIST, null);
    }

    public void removeUserFromList (int userID) {
        Game.getPlayers().remove(userID);
    }
}
