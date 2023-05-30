package ClientUIHandling.actions;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;

public class ActionPlayerLost implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        Log.i("INFO", "PLAYERLOSTRECEIVED");

        String[] splitMessage = clientMessage.split(" ");

        int id = Integer.parseInt(splitMessage[2]);

        if (GameViewActivity.clientID == id) {
            TextView playerNameTextView = activity.findViewById(R.id.textView_playerName_spec);
            String playerNameInputValue = String.format(activity.getString(R.string.playerName_info_text), "Spectator"); // Todo dynamisch
            playerNameTextView.setText(playerNameInputValue);
            playerNameTextView.setTextColor(Color.RED);
            playerNameTextView.setTypeface(null, Typeface.BOLD);

            // Todo move in another class
//            Game.reset();
//            LobbyViewActivity.userList.clear();
            // Todo also stopConnection()
            // Todo playerMarker invisible

            Toast.makeText(activity, "YOU LOST!", Toast.LENGTH_LONG).show();

//            Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
//
//            intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
//            activity.startActivity(intent);
        }
    }
}
