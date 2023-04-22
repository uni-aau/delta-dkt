package delta.dkt.activities;

import ClientUIHandling.handlers.map_movements.PositionHandler;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.Constants;
import delta.dkt.R;

import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;


public class GameViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        findViewById(R.id.button_property_infos).setOnClickListener(view -> switchToPropertyActivity());

        MainActivity.subscribeToLogic(Constants.GameViewActivityType, this);

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

                var figure = findViewById(R.id.player1);
                Log.d("Movement", "Relative coordinates: " + new PointF(absoluteX, absoluteY) + " " + new PointF(relativeX, relativeY));

                figure.setX(absoluteX);
                figure.setY(absoluteY);

                return true;
            }
        });
    }

    protected void switchToPropertyActivity () {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }

    /**
     * This method will set update the location of a players figure to the requested destination.
     * @param destination The destinations number, from 1: Start to 39: Last field.
     * @param _player The player figure that is to be moved, ranging from 1 to 6.
     */
    private void updatePlayerPosition (int destination, int _player) {
        ImageView map = findViewById(R.id.imageView);
        int figureIdentifier = getResources().getIdentifier("player" + _player, "id", getPackageName());
        ImageView figure = findViewById(figureIdentifier);

        var pos = PositionHandler.calculateFigurePosition(destination, _player, figure, map);
        figure.setX(pos.x);
        figure.setY(pos.y);
    }
}