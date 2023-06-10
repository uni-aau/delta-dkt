package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.activities.MainMenuActivity;

public class ActionRemoveUserFromUserList implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        if(MainMenuActivity.role){
            int id = Integer.parseInt(clientMessage.split(" ")[1]);
            ServerActionHandler.triggerAction(Constants.PREFIX_REMOVE_USER_FROM_LIST, id);
        }
    }
}
