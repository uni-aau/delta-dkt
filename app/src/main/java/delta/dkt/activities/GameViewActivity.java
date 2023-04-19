package delta.dkt.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.Constants;
import delta.dkt.R;

public class GameViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        findViewById(R.id.button_property_infos).setOnClickListener(view -> switchToPropertyActivity());

        MainActivity.subscribeToLogic(Constants.GameViewActivityType, this);
    }

    protected void switchToPropertyActivity() {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }
}