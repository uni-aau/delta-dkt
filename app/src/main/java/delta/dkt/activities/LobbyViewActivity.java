package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_ADD_USER_TO_LIST;
import static ClientUIHandling.Constants.PREFIX_CLOSE_GAME;
import static ClientUIHandling.Constants.PREFIX_GAME_START;
import static ClientUIHandling.Constants.PREFIX_REMOVE_USER_FROM_LIST;
import static delta.dkt.activities.MainActivity.user;
import static delta.dkt.activities.MainMenuActivity.role;

import ClientUIHandling.Config;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MainActivity.subscribeToLogic(Constants.LOBBYVIEW_ACTIVITY_TYPE, this);
        if(!role) {
            Log.d("INFO","SUBSCRIBED IN LOBBY");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        Button backButton = findViewById(R.id.backbtn);
        startButton = findViewById(R.id.startbtn);

        // Visualizes grayed out start button for non hoster
        if(!role) {
            disableStartButton();
        }

        // Everything which belongs to the Recycler View:
        recyclerView = findViewById(R.id.lobbyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserNameAdapter(userList);
        recyclerView.setAdapter(adapter);

        // Adding User to the UserList
        welcomeToLobby();

        backButton.setOnClickListener(view -> leavingTheLobby());

        startButton.setOnClickListener(view -> {
            Log.d("Start", "Sending start action to server!");
            ServerActionHandler.triggerAction(PREFIX_GAME_START, "");
        });

        if(Config.Skip && Config.DEBUG) {
            startButton.performClick();
        }
    }

    // Action when player presses back on mobile phone
    @Override
    public void onBackPressed() {
        openPlayerLeavePopUp();
    }


    //---------------------------ALL METHODS:---------------------------//

    // This Method ass user to UserList und updates the List
    public void welcomeToLobby () {
        if(role) {
            ServerActionHandler.triggerAction(PREFIX_ADD_USER_TO_LIST, new Object[]{user, 1});

            Toast.makeText(LobbyViewActivity.this, "Users Total: " + userList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    // This Method removes the User from the UserList, updates the List and Closes the game/server
    public void leavingTheLobby() {
        ServerActionHandler.triggerAction(PREFIX_REMOVE_USER_FROM_LIST, user);
        ServerActionHandler.triggerAction(PREFIX_CLOSE_GAME,"");

        Toast.makeText(LobbyViewActivity.this, "Users Total: "+userList.size(), Toast.LENGTH_SHORT).show();
    }

    private void disableStartButton() {
        startButton.setEnabled(false);
        startButton.setBackgroundResource(R.drawable.host_btn_background_disabled);
    }

    private void openPlayerLeavePopUp() {
        ConstraintLayout popUpConstraintLayout = findViewById(R.id.playerLeavePopUpConstraint);
        View view = LayoutInflater.from(this).inflate(R.layout.player_leave_pop_up_window, popUpConstraintLayout);

        Button leaveGame = view.findViewById(R.id.button_leaveGame_yes);
        Button cancelLeaveGame = view.findViewById(R.id.button_leaveGame_no);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        cancelLeaveGame.setOnClickListener(view1 -> alertDialog.dismiss());
        leaveGame.setOnClickListener(view1 -> leavingTheLobby());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}