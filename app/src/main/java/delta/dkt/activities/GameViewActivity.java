package delta.dkt.activities;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

                Log.d("Movement", "Relative coordinates: " + new PointF(absoluteX, absoluteY) + " " + new PointF(relativeX, relativeY));

                return true;
            }
        });
    }

    protected void switchToPropertyActivity () {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }

}