package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.MainMenuActivity;

public class ActionRemoveUserFromUserList implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        String[] args = clientMessage.replace(PREFIX_REMOVE_USER_FROM_LIST, "").trim().split(";"); // Holt sich Args nach dem Prefix
        Log.d("[CLIENT]_Remove_User_From_User_List", "JUHU! User was removed from List. Parameter: "+ args[0] );

        Intent intent = new Intent(activity, MainMenuActivity.class);
        activity.startActivity(intent);
    }
}
