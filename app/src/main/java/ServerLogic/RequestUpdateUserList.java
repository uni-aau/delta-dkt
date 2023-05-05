package ServerLogic;


import static ClientUIHandling.Constants.MAINMENU_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;

import android.util.Log;

import network2.ServerNetworkClient;

public class RequestUpdateUserList implements ServerActionInterface{

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER]:Update_UserList", "Update UserList Request received: " + parameters);

        server.broadcast(MAINMENU_ACTIVITY_TYPE, PREFIX_UPDATE_USER_LIST, new String[]{(String) parameters});


        /*MainMenuActivity mainMenuActivity = new MainMenuActivity();
        LobbyViewActivity lobbyViewActivity = new LobbyViewActivity();

        Button host = ((Button) mainMenuActivity.findViewById(R.id.host_button));
        Button back = ((Button) lobbyViewActivity.findViewById(R.id.backbtn));

        if(host.isSelected()){
            Log.d("[SERVER]:Update_UserList (MAIN MENU)", "Update UserList Request received");
            server.broadcast(MAINMENU_ACTIVITY_TYPE+":"+ PREFIX_UPDATE_USER_LIST + parameters);
        }else{
            Log.d("[SERVER]:Update_UserList (LOBBY)", "Update UserList Request received");
            server.broadcast(LOBBYVIEW_ACTIVITY_TYPE+":"+ PREFIX_UPDATE_USER_LIST + parameters);
        }*/
    }
}
