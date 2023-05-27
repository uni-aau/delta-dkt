package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_END_GAME;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

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
        String[] args = clientMessage.replace(PREFIX_END_GAME, "").split(";");

        HashMap<String, Integer> stats = new HashMap<>();

        for (String arg : args) {
            String[] parts = arg.split("#!#");
            String name = parts[0];
            int wealth = Integer.parseInt(parts[1]);
            stats.put(name, wealth);
        }

        ArrayList<Integer> valuesList = new ArrayList<>(stats.values());


        Log.d("[CLIENT]_GAME_END", "JUHU! Game has ended// Name: "  + " "+ stats.keySet());



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
