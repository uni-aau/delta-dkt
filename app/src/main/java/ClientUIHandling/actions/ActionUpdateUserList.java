package ClientUIHandling.actions;


import static ClientUIHandling.Constants.PREFIX_UPDATE_USER_LIST;
import static delta.dkt.activities.LobbyViewActivity.userList;


import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;


public class ActionUpdateUserList implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        String[] args = clientMessage.replace(PREFIX_UPDATE_USER_LIST, "").trim().split(";"); // Holt sich Args nach dem Prefix
        updateLobbyInfo(activity);

        System.out.println(clientMessage);
        userList.clear();
        for (String user : args) {
            userList.add(user);
        }
        Log.d("[CLIENT]_Update_User_List", "JUHU! UserList is up to date");
    }

    private void updateLobbyInfo(Activity activity) {
        String combinedString = activity.getString(R.string.TotalPlayers) + " " + userList.size();
        ((TextView) activity.findViewById(R.id.currentPlayersTextView)).setText(combinedString);
    }
}
