package ClientUIHandling.hostGameActions;


import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;
import android.content.Intent;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.LobbyViewActivity;


public class ActionHostGame implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        String[] args = clientMessage.replace(PREFIX_HOST_NEW_GAME, "").trim().split(";"); // Holt sich Args nach dem Prefix
        Log.d("[CLIENT]:Host_Game ", "Juhu! Server started!");

        Intent intent = new Intent(activity, LobbyViewActivity.class);
        activity.startActivity(intent);
    }
}
