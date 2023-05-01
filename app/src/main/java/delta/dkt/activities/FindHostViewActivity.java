package delta.dkt.activities;


import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.Constants;
import delta.dkt.R;


public class FindHostViewActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    HostAdapter adapter;
    ArrayList<String> hostList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_host_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button backButton = findViewById(R.id.backbtn);
        Button joinButton = findViewById(R.id.joinbtn);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);

        hostList = new ArrayList<>();

        hostList.add("Host1");
        hostList.add("Host2");
        hostList.add("Host3");

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HostAdapter(this, hostList);
        recyclerView.setAdapter(adapter);


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

        MainActivity.subscribeToLogic(Constants.FindHostViewActivityType, this);

    }
}