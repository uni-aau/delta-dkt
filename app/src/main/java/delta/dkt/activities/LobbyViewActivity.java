package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
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
        String userName = getIntent().getStringExtra("stringname");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
            }
        });

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GameViewActivity.class);
                startActivity(intent);
            }
        });
    }
}