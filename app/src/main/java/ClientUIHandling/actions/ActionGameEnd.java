package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_END_GAME;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;


public class ActionGameEnd implements ClientActionInterface {


    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {


        // Receiving the WinnerRankingList from the Server:
        receiveWinnerRanking(clientMessage);

        // Show the Winner Pop Up Window:
        showWinnerPopUpWindow(activity);




        // Closing the Client Connection and switching to the Main Menu
       /* try {
            ClientHandler.getClient().stopConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Game.reset();
        LobbyViewActivity.userList.clear();

         Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
         intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
         activity.startActivity(intent);
        */

    }



    //----------------------ALL METHODS------------------------//

    private void receiveWinnerRanking(String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_END_GAME, "").split(";");

        HashMap<String, Integer> stats = new HashMap<>();

        for (String arg : args) {
            String[] parts = arg.split("#!#");
            String name = parts[0];
            int wealth = Integer.parseInt(parts[1]);
            stats.put(name, wealth);
        }
        Log.d("[CLIENT]_GAME_END", "JUHU! Game has ended// Name: "  + " "+ stats.keySet());
    }

    public void showWinnerPopUpWindow(AppCompatActivity activity) {
        // Everything that is needed to create the popUp Window:
        GameViewActivity game = (GameViewActivity) activity;
        ConstraintLayout popUpConstraintLayout = game.findViewById(R.id.winnerPopUpLayout);
        View view = LayoutInflater.from(activity).inflate(R.layout.winner_podium_pop_up, popUpConstraintLayout);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        // Get View from xml:
        Button okButtonWinner = view.findViewById(R.id.okButtonWinner);

        // Closing the Client Connection and switching to the Main Menu
        okButtonWinner.setOnClickListener(view1 -> {
            Log.d("[CLIENT]_GAME_END", "Beginning of Onclick");
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
            Log.d("[CLIENT]_GAME_END", "End of On Click");
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }
}
