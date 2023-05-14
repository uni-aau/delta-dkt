package ClientUIHandling.actions;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionGameEnd implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        Toast.makeText(activity, clientMessage, Toast.LENGTH_LONG).show();

        try {
            ClientHandler.getClient().stopConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Game.reset();
        LobbyViewActivity.userList.clear();

        Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);

        intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
        activity.startActivity(intent);


    }
}
