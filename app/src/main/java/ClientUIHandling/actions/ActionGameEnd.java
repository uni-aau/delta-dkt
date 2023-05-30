package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_END_GAME;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ClientUIHandling.ClientActionInterface;
import ClientUIHandling.ClientHandler;
import delta.dkt.R;
import delta.dkt.activities.GameViewActivity;
import delta.dkt.activities.LobbyViewActivity;
import delta.dkt.activities.MainActivity;
import delta.dkt.activities.MainMenuActivity;
import delta.dkt.logic.structure.Game;


public class ActionGameEnd implements ClientActionInterface {

    private HashMap<String, Integer> ranking;

    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {

        // Everything that is needed to create the popUp Window:
        GameViewActivity game = (GameViewActivity) activity;
        ConstraintLayout popUpConstraintLayout = game.findViewById(R.id.winnerPopUpLayout);
        View view = LayoutInflater.from(activity).inflate(R.layout.winner_podium_pop_up, popUpConstraintLayout);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);  // Prevent closing when touching outside the window
        Button okButtonWinner = view.findViewById(R.id.okButtonWinner);


        // Receiving the WinnerRankingList from the Server:
        ranking= receiveWinnerRanking(clientMessage);

        // Setting TextViews with Values:
        setTextViews(view);

        // Close client connection and reset the game:
        try {
            ClientHandler.getClient().stopConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Game.reset();
        LobbyViewActivity.userList.clear();



        // Switching to the Main Menu when ok Button is clicked:
        okButtonWinner.setOnClickListener(view1 -> {
            Log.d("[CLIENT]_GAME_END", "Beginning of Onclick");

            Intent intent = new Intent(activity.getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(MainActivity.INTENT_PARAMETER, MainMenuActivity.username);
            activity.startActivity(intent);
            Log.d("[CLIENT]_GAME_END", "End of On Click");

            alertDialog.dismiss();
        });

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

    }



    //----------------------ALL METHODS------------------------//


    // This method is setting the Textviews in the XML:
    private void setTextViews(View view) {

        // Winner Names Textviews:
        TextView firstWinnerTV = view.findViewById(R.id.firstWinnerTV);
        TextView secondWinnerTV = view.findViewById(R.id.secondWinnerTV);
        TextView thirdWinnerTV = view.findViewById(R.id.thirdWinnerTV);
        TextView fourthWinnerTV = view.findViewById(R.id.fourthWinnerTV);
        TextView fifthWinnerTV = view.findViewById(R.id.fifthWinnerTV);
        TextView sixthWinnerTV = view.findViewById(R.id.sixthWinnerTV);

        // Winner Wealth Textviews:
        TextView firstWealthTV = view.findViewById(R.id.firstWealthTV);
        TextView secondWealthTV = view.findViewById(R.id.secondWealthTV);
        TextView thirdWealthTV = view.findViewById(R.id.thirdWealthTV);
        TextView fourthWealthTV = view.findViewById(R.id.fourthWealthTV);
        TextView fifthWealthTV = view.findViewById(R.id.fifthWealthTV);
        TextView sixthWealthTV = view.findViewById(R.id.sixthWealthTV);

        // Ranking 4-6 Place:
        TextView fourthRankingTV = view.findViewById(R.id.fourthPlaceTV);
        TextView fifthRankingTV = view.findViewById(R.id.fifthPlaceTV);
        TextView sixthRankingTV = view.findViewById(R.id.sixthPlaceTV);


        // Clear previous text
        firstWinnerTV.setText("");
        secondWinnerTV.setText("");
        thirdWinnerTV.setText("");
        fourthWinnerTV.setText("");
        fifthWinnerTV.setText("");
        sixthWinnerTV.setText("");

        firstWealthTV.setText("");
        secondWealthTV.setText("");
        thirdWealthTV.setText("");
        fourthWealthTV.setText("");
        fifthWealthTV.setText("");
        sixthWealthTV.setText("");

        fourthRankingTV.setText("");
        fifthRankingTV.setText("");
        sixthRankingTV.setText("");


        // Checking size of the List and than setting the Textviews:
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(ranking.entrySet());

        if (entryList.size() >= 1) {
            Map.Entry<String, Integer> firstEntry = entryList.get(0);
            String firstWinnerName = firstEntry.getKey();
            int firstWinnerWealth = firstEntry.getValue();

            firstWinnerTV.setText(firstWinnerName);
            firstWealthTV.setText(String.valueOf(firstWinnerWealth));
        }

        if (entryList.size() >= 2) {
            Map.Entry<String, Integer> secondEntry = entryList.get(1);
            String secondWinnerName = secondEntry.getKey();
            int secondWinnerWealth = secondEntry.getValue();

            secondWinnerTV.setText(secondWinnerName);
            secondWealthTV.setText(String.valueOf(secondWinnerWealth));
        }

        if (entryList.size() >= 3) {
            Map.Entry<String, Integer> thirdEntry = entryList.get(2);
            String thirdWinnerName = thirdEntry.getKey();
            int thirdWinnerWealth = thirdEntry.getValue();

            thirdWinnerTV.setText(thirdWinnerName);
            thirdWealthTV.setText(String.valueOf(thirdWinnerWealth));
        }

        if (entryList.size() >= 4) {
            Map.Entry<String, Integer> fourthEntry = entryList.get(3);
            String fourthWinnerName = fourthEntry.getKey();
            int fourthWinnerWealth = fourthEntry.getValue();

            fourthWinnerTV.setText(fourthWinnerName);
            fourthWealthTV.setText(String.valueOf(fourthWinnerWealth));
            fourthRankingTV.setText("4");
        }

        if (entryList.size() >= 5) {
            Map.Entry<String, Integer> fifthEntry = entryList.get(4);
            String fifthWinnerName = fifthEntry.getKey();
            int fifthWinnerWealth = fifthEntry.getValue();

            fifthWinnerTV.setText(fifthWinnerName);
            fifthWealthTV.setText(String.valueOf(fifthWinnerWealth));
            fifthRankingTV.setText("5");
        }

        if (entryList.size() >= 6) {
            Map.Entry<String, Integer> sixthEntry = entryList.get(5);
            String sixthWinnerName = sixthEntry.getKey();
            int sixthWinnerWealth = sixthEntry.getValue();

            sixthWinnerTV.setText(sixthWinnerName);
            sixthWealthTV.setText(String.valueOf(sixthWinnerWealth));
            sixthRankingTV.setText("6");
        }
    }



    // This Method is returning the Hashmap with the WinnerRanking
    private HashMap<String,Integer> receiveWinnerRanking(String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_END_GAME, "").split(";");

        HashMap<String, Integer> stats = new HashMap<>();

        for (String arg : args) {
            String[] parts = arg.split("#!#");
            String name = parts[0];
            int wealth = Integer.parseInt(parts[1]);
            stats.put(name, wealth);
        }
        Log.d("[CLIENT]_GAME_END", "JUHU! Game has ended// Name: "  + " "+ stats.keySet());
        return stats;
    }

}
