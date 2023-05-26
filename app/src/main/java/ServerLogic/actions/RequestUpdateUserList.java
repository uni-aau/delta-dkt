package ServerLogic.actions;


import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GET_IP;
import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;

import android.util.Log;

import java.util.ArrayList;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.activities.MainMenuActivity;
import network2.ServerNetworkClient;

public class RequestUpdateUserList implements ServerActionInterface {

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Update_UserList", "Update UserList Request received");
        if(!(parameters instanceof ArrayList)){
            Log.e("ERROR","WRONG PARAMETER, EXPECTED ARRAYLIST");
            return;
        }
        ArrayList<String> list = (ArrayList<String>) parameters;

        server.broadcast(LOBBYVIEW_ACTIVITY_TYPE, PREFIX_UPDATE_USER_LIST, list.toArray(new String[]{}));
        ServerActionHandler.triggerAction(PREFIX_GET_IP, MainMenuActivity.ip);
    }
}
