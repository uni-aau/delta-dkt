package ClientUIHandling;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionHostGame implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        Log.d("CLIENT_HOST", "Client is in da Lobby!!");

        MainMenuActivity mainMenuActivity = (MainMenuActivity) activity;

        Intent intent = new Intent(activity, LobbyViewActivity.class);
        intent.putExtra(INTENT_PARAMETER, "UserName");
        mainMenuActivity.startActivity(intent);
    }
}
