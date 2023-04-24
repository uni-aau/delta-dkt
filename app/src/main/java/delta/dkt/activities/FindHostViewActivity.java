package delta.dkt.activities;


import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import delta.dkt.R;


public class FindHostViewActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    ArrayList<String> hostList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_host_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        Button backButton = findViewById(R.id.backbtn);
        Button joinButton = findViewById(R.id.joinbtn);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);



        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        joinButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

    }
}