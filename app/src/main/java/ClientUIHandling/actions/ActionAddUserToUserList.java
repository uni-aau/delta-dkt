package ClientUIHandling.actions;

import static ClientUIHandling.Constants.LOBBYVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;

import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainMenuActivity;


public class ActionAddUserToUserList implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ADD_USER_TO_LIST, "").trim().split(";"); // Holt sich Args nach dem Prefix
        Log.d("[CLIENT]_Add_User_To_User_List", "JUHU! User was added to the List. Parameter: "+ args[0] );
        if(MainMenuActivity.role){
            ServerActionHandler.triggerAction(PREFIX_ADD_USER_TO_LIST, args[0]);
        }
        LobbyViewActivity.userList.add(args[0]);
    }
}
