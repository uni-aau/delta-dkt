package ClientUIHandling.actions;


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
