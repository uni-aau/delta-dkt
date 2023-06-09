package ClientUIHandling.actions;


import static ClientUIHandling.Constants.PREFIX_LEAVE_LOBBY;


import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionLeaveLobby implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        String[] args = clientMessage.replace(PREFIX_LEAVE_LOBBY, "").trim().split(";");
        String leavingUser = args[0];

        Log.d("[CLIENT]:Leave_Lobby", leavingUser+" left!");

        // Update the user card background in the LobbyViewActivity
        LobbyViewActivity lobbyActivity = (LobbyViewActivity) activity;
        lobbyActivity.runOnUiThread(() -> lobbyActivity.updateUserBackground(leavingUser));



        // Close client connection
        try {
            ClientHandler.getClient().stopConnection();
            Log.d("[CLIENT]:Client_closed", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
        activity.startActivity(intent);








        

    }
}
