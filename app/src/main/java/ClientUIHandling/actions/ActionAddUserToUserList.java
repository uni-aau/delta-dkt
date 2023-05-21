package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainMenuActivity;


public class ActionAddUserToUserList implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_ADD_USER_TO_LIST, "").trim().split(" "); // Holt sich Args nach dem Prefix


        Log.d("[CLIENT]_Add_User_To_User_List", "JUHU! User was added to the List. Parameter: "+ clientMessage);
        if(MainMenuActivity.role){
            ServerActionHandler.triggerAction(PREFIX_ADD_USER_TO_LIST,new Object[]{ args[0], Integer.parseInt(args[1])});
        }
        LobbyViewActivity.userList.add(args[0]);
    }
}
