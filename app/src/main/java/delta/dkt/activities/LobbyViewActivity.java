package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;
import static ClientUIHandling.Constants.PREFIX_CLOSE_GAME;
import static ClientUIHandling.Constants.PREFIX_GAME_START;
import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;
import static delta.dkt.activities.MainActivity.user;
import static delta.dkt.activities.MainMenuActivity.role;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;


public class LobbyViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserNameAdapter adapter;
    public static ArrayList<String> userList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.subscribeToLogic(Constants.LOBBYVIEW_ACTIVITY_TYPE, this);
        if(!role) {
            System.out.println("SUBSCRIBED IN LOBBY");
        }

        System.out.println("SIZE: "+userList.size());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level



        // Get Views from the Lobby xml:
        Button backButton = findViewById(R.id.backbtn);
        Button startButton = findViewById(R.id.startbtn);
        //boolean role = getIntent().getExtras().getBoolean("role");


        // Everything which belongs to the Recycler View:
        recyclerView = findViewById(R.id.lobbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserNameAdapter(this, userList, role);
        recyclerView.setAdapter(adapter);


        // Adding User to the UserList
        welcomeToLobby();


        //---BACK BUTTON---  (Everything that happens when back button is clicked)
        backButton.setOnClickListener(view -> {
            leavingTheLobby();
        });


        //---START BUTTON---  (Everything that happens when Start button is clicked)
        startButton.setOnClickListener(view -> {
            Log.d("Start", "Sending start action to server!");
            ServerActionHandler.triggerAction(PREFIX_GAME_START, "");
        });

    }



    //---------------------------ALL METHODS:---------------------------//

    // This Method ass user to UserList und updates the List
    public void welcomeToLobby () {
        if(role) {
            ServerActionHandler.triggerAction(PREFIX_ADD_USER_TO_LIST, user);

            Toast.makeText(LobbyViewActivity.this, "Users Total: " + userList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    // This Method removes the User from the UserList, updates the List and Closes the game/server
    public void leavingTheLobby() {
        ServerActionHandler.triggerAction(PREFIX_REMOVE_USER_FROM_LIST, user);


        ServerActionHandler.triggerAction(PREFIX_CLOSE_GAME,""); // TO DO -> (implement close server and close client in actions)

        Toast.makeText(LobbyViewActivity.this, "Users Total: "+userList.size(), Toast.LENGTH_SHORT).show();
    }



}