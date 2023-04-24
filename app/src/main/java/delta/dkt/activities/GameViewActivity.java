package delta.dkt.activities;

import ClientUIHandling.handlers.map_movements.PositionHandler;
import ServerLogic.ServerActionHandler;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.Constants;
import delta.dkt.R;

import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static delta.dkt.R.id.imageView;


public class GameViewActivity extends AppCompatActivity {
    int[] locations = new int[6];
    int clientID = 1; //todo get the clients id - from 1 to 6

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        findViewById(R.id.button_property_infos).setOnClickListener(view -> switchToPropertyActivity());

        MainActivity.subscribeToLogic(Constants.GameViewActivityType, this);


        handleMovementRequests();
    }

    protected void switchToPropertyActivity () {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }



    /**
     * This method handles the movement requests of a client, thus sending the request to server.
     */
    private void handleMovementRequests () {
        Button btnDice = findViewById(R.id.button_roll_dice);
        ImageView map = findViewById(imageView);


        //* Wait for the imageView to load, then update the default locations.
        map.post(() -> updatePlayerPosition(locations[0], 1));
        map.post(() -> updatePlayerPosition(locations[1], 2));
        map.post(() -> updatePlayerPosition(locations[2], 3));
        map.post(() -> updatePlayerPosition(locations[3], 4));
        map.post(() -> updatePlayerPosition(locations[4], 5));
        map.post(() -> updatePlayerPosition(locations[5], 6));
        map.post(() -> PositionHandler.setLogs(true));


        btnDice.setOnClickListener(view -> {
            Log.d("Movement", "Sending movement request to server!");
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, clientID);
        });



        var imageView = findViewById(R.id.imageView);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

                var map = findViewById(R.id.imageView);

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
     * @param destination The destinations number, from 1: Start to 39: Last field.
     * @param _player The player figure that is to be moved, ranging from 1 to 6.
     */
    private void updatePlayerPosition (int destination, int _player) {
        ImageView map = findViewById(imageView);
        int figureIdentifier = getResources().getIdentifier("player" + _player, "id", getPackageName());
        ImageView figure = findViewById(figureIdentifier);

        var pos = PositionHandler.calculateFigurePosition(destination, _player, figure, map);
        figure.setX(pos.x);
        figure.setY(pos.y);
    }
}