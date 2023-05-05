package ClientUIHandling;


import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;


import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.R;


public class ActionUpdateUserList implements ClientActionInterface{

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        // Here the TextView currentPlayers in the Lobby Activity needs to update
        String[] args = clientMessage.replace(PREFIX_UPDATE_USER_LIST, "").trim().split(";"); // Holt sich Args nach dem Prefix
        Log.d("[CLIENT]_Update_User_List", "JUHU! UserList Update Request received " + args[0]);


        TextView temp = (TextView) activity.findViewById(R.id.currentPlayersTextView);
        temp.setText("WUZUP");



        Log.d("[CLIENT]_Update_User_List", "TEXT VIEW WAS UPDATED!");

    }



}
