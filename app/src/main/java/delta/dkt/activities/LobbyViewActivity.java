package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_GAME_START;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;


public class LobbyViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserNameAdapter adapter;
    public static ArrayList<String> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        // Get Views from the Lobby xml:
        Button backButton = findViewById(R.id.backbtn);
        Button startButton = findViewById(R.id.startbtn);
        TextView totalPlayers = findViewById(R.id.currentPlayersTextView);


        // Get User Name, add it to the List and set the TotalPlayersNumber:
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);
        //userList.add();
        //String combinedString = getString(R.string.TotalPlayers) + userList.size();
        //totalPlayers.setText("???");



        // Everything which belongs to the Recycler View:
        recyclerView = findViewById(R.id.lobbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserNameAdapter(this, userList);
        recyclerView.setAdapter(adapter);


        //---BACK BUTTON---  (Everything that happens when back button is clicked)
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            removeUserFromUserList(newUser);
            startActivity(intent);
        });


        //---START BUTTON---  (Everything that happens when Start button is clicked)
        startButton.setOnClickListener(view -> {
            Log.d("Start", "Sending start action to server!");
            ServerActionHandler.triggerAction(PREFIX_GAME_START, "");
        });

        MainActivity.subscribeToLogic(Constants.LOBBYVIEW_ACTIVITY_TYPE, this);
    }



    //---------------------------ALL METHODS:---------------------------//


    // This Method adds new User to the UserList
    public void addUserToUserList(String newUser){
        userList.add(newUser);
        Toast.makeText(LobbyViewActivity.this, "Users Total: "+userList.size(), Toast.LENGTH_SHORT).show();
    }

    // This Method removes the User from the UserList
    public void removeUserFromUserList(String newUser) {
        userList.removeAll(List.of(newUser));
        Toast.makeText(LobbyViewActivity.this, "Users Total: "+userList.size(), Toast.LENGTH_SHORT).show();
    }
}