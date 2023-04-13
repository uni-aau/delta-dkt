package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

import delta.dkt.R;

public class LobbyViewActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    UserNameAdapter adapter;
    ArrayList<String> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_view);

        Button backButton = findViewById(R.id.backbtn);
        Button startButton = findViewById(R.id.startbtn);

        userList = new ArrayList<>();
        String newUser = getIntent().getStringExtra("stringname");
        userList.add(newUser);

        //Only for Testing purpose adding some more Players:
        userList.add("Angelo");
        userList.add("Doug");
        userList.add("Slim");
        userList.add("Spider Man");
        userList.add("Kim");

        recyclerView = findViewById(R.id.lobbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserNameAdapter(this, userList);
        recyclerView.setAdapter(adapter);




        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.putExtra("stringname", newUser);
            startActivity(intent);
        });

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), GameViewActivity.class);
            intent.putExtra("stringname", newUser);
            startActivity(intent);
        });
    }
}