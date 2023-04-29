package ClientUIHandling;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainMenuActivity;

public class ActionHostNewGame implements ClientActionInterface {

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        MainMenuActivity mainMenuActivity = (MainMenuActivity) activity;
        Intent intent = new Intent(mainMenuActivity, LobbyViewActivity.class);
        intent.putExtra(INTENT_PARAMETER, "UserName");
        mainMenuActivity.startActivity(intent);
    }
}
