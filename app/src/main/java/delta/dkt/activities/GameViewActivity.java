package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_GAME_START_STATS;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;
import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;
import static ClientUIHandling.Constants.PREFIX_PLAYER_BUYPROPERTY;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_RECEIVE;
import static delta.dkt.R.id.imageView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ClientUIHandling.handlers.positioning.PositionHandler;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;
import delta.dkt.logic.structure.Field;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;


public class GameViewActivity extends AppCompatActivity {
    public static int clientID = -1; // ID gets set by server
    public static int players = -1; // players gets set by server
    int[] locations = {1, 1, 1, 1, 1, 1};
    Button btnDice;
    ImageView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        btnDice = findViewById(R.id.button_roll_dice);
        map = findViewById(imageView);
        findViewById(R.id.button_property_infos).setOnClickListener(view -> switchToPropertyActivity());

        MainActivity.subscribeToLogic(Constants.GAMEVIEW_ACTIVITY_TYPE, this);
        if (MainMenuActivity.role) {
            ServerActionHandler.triggerAction(PREFIX_GET_SERVER_TIME, clientID); // Get game time
            ServerActionHandler.triggerAction(PREFIX_INIT_PLAYERS, String.valueOf(clientID)); // Set player & handle dice perms
            ServerActionHandler.triggerAction(PREFIX_GAME_START_STATS, String.valueOf(Game.getPlayers().size())); // Update player stats
        }

        displayPlayers(players);
        handleMovementRequests();
    }

    protected void switchToPropertyActivity() {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }


    /**
     * This method handles the movement requests of a client, thus sending the request to server.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void handleMovementRequests() {
        //? Places all figures on their designated position inside the start field.
        for (int i = 0; i < locations.length; i++) {
            int index = i;
            map.post(() -> updatePlayerPosition(locations[index], index + 1));
        }

        map.post(() -> PositionHandler.setLogs(true));


        btnDice.setOnClickListener(view -> {
            Log.d("Movement", "Sending movement request to server!");


            ClientHandler.sendMessageToServer(Constants.GAMEVIEW_ACTIVITY_TYPE + ":" + PREFIX_ROLL_DICE_RECEIVE + " " + clientID);


        });


        map.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

            btnDice.performClick();
            return true;
        });

    }

    /**
     * This method will set update the location of a players figure to the requested destination.
     *
     * @param destination The destinations number, from 1: Start to 39: Last field.
     * @param _player The player figure that is to be moved, ranging from 1 to 6.
     */
    public void updatePlayerPosition(int destination, int _player) {
        int figureIdentifier = getResources().getIdentifier("player" + _player, "id", getPackageName());
        ImageView figure = findViewById(figureIdentifier);

        if (map == null || figure == null) {
            Log.e("Movement-GameView", String.format("Aborting movement! Because %s", (map == null ? "the map has not been found!" : "the players figure couldn't be found!")));
            return;
        }

        var pos = PositionHandler.calculateFigurePosition(destination, _player, figure, map);
        figure.setX(pos.x);
        figure.setY(pos.y);
        //always
        checkIfPropertyCanBeBought(destination);
    }


    public void checkIfPropertyCanBeBought(int destination){
        Field field = Game.getMap().getField(destination);
        Player player = Game.getPlayers().get(players);
        if(field instanceof Property &&  ((Property) field).getOwner() == null && ((Property) field).getPrice() <= player.getCash()){
           AlertDialog dialog = createBuilder((Property) field, player);
           dialog.show();
        }
    }

    private AlertDialog createBuilder(Property prop, Player player){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Willst du das Grundstück \""+ prop.getName()+"\" für "+prop.getPrice()+"€ kaufen?")
                .setPositiveButton("Kaufen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //buy the property .. send the request to the server
                        ServerActionHandler.triggerAction(PREFIX_PLAYER_BUYPROPERTY, players);
                    }
                })
                .setNegativeButton("Nicht kaufen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        //Usually nothing happens
                    }
                });
        return builder.create();
    }

    public void enableDice() {
        btnDice.setEnabled(true);
        btnDice.setBackgroundResource(R.drawable.host_btn_background);
        map.setEnabled(true);
    }

    public void disableDice() {
        btnDice.setEnabled(false);
        btnDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
        map.setEnabled(false); // prevent touch event
    }

    /**
     * This method will set the figure's for the request amount of players visible.
     *
     * @param count Amount of players of which the figures are going to be made visible.
     */
    public void displayPlayers(int count) {
        Log.d("[GameView] Action", "Enabling players " + count);

        if (count <= Config.MAX_CLIENTS) {
            for (int i = 1; i <= count; i++) {
                ImageView figure = findViewById(getResources().getIdentifier("player" + i, "id", getPackageName()));

                if (figure == null) continue;

                figure.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("[UI] Action Error", String.format("Error - Less player markers (%d) than players (%d)!", Config.MAX_CLIENTS, count));
            Toast.makeText(this, "There was an error while adding another player - Check error logs!", Toast.LENGTH_SHORT).show();
        }
    }
}