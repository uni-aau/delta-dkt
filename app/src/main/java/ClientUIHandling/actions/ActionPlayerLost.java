package ClientUIHandling.actions;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;

public class ActionPlayerLost implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "PLAYERLOSTRECEIVED");

        String[] splitMessage = clientMessage.split(" ");

        int id = Integer.parseInt(splitMessage[2]);

        if(GameViewActivity.clientID == id) {

            Game.reset();
            LobbyViewActivity.userList.clear();

            Toast.makeText(activity, "YOU LOST!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);

            intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);
        }
    }
}
