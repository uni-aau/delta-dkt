package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_GAME_START_STATS;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;
import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static delta.dkt.R.id.imageView;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ClientUIHandling.handlers.positioning.PositionHandler;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;


public class GameViewActivity extends AppCompatActivity {
    public static int clientID = -1; // ID gets set by server
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
        ServerActionHandler.triggerAction(PREFIX_GET_SERVER_TIME, 1); // Get game time
        ServerActionHandler.triggerAction(PREFIX_INIT_PLAYERS, String.valueOf(clientID)); // Set player & handle dice perms
        ServerActionHandler.triggerAction(PREFIX_GAME_START_STATS, String.valueOf(Game.getPlayers().size())); // Update player stats

        handleMovementRequests();
    }

    protected void switchToPropertyActivity() {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }


    /**
     * This method handles the movement requests of a client, thus sending the request to server.
     */
    private void handleMovementRequests() {
        //? Places all figures on their designated position inside the start field.
        for(int i = 0; i < locations.length; i++) {
            int index = i;
            map.post(() -> updatePlayerPosition(locations[index], index + 1));
        }

        map.post(() -> PositionHandler.setLogs(true));


        btnDice.setOnClickListener(view -> {
            Log.d("Movement", "Sending movement request to server!");
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, clientID);
        });


        var imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

                map = findViewById(R.id.imageView);

                float x = event.getX();
                float y = event.getY();

                float relativeX = x / map.getWidth();
                float relativeY = y / map.getHeight();

                float absoluteX = map.getX() + (relativeX * map.getWidth());
                float absoluteY = map.getY() + (relativeY * map.getHeight());

                Log.d("Movement", "Relative coordinates: " + new PointF(absoluteX, absoluteY) + " " + new PointF(relativeX, relativeY));

                btnDice.performClick();

                return true;
            }
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
}