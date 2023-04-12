package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import delta.dkt.R;

public class LobbyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_view);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Force landscape screen at activity level

        Button backbtn = findViewById(R.id.backbtn);
        Button startbtn = findViewById(R.id.startbtn);


        backbtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent);
        });

        startbtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameViewActivity.class);
            startActivity(intent);
        });
    }
}