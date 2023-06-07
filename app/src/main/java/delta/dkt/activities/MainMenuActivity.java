package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;
import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;
import static delta.dkt.activities.MainActivity.logic;
import static delta.dkt.activities.MainActivity.user;

import ClientUIHandling.Config;
import ClientUIHandling.handlers.notifications.SnackBarHandler;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;
import network2.NetworkClientConnection;
import network2.ServerNetworkClient;


public class MainMenuActivity extends AppCompatActivity {

    ServerNetworkClient server;
    NetworkClientConnection client;

    public static String username; // Todo - Move into Main Activity??
    public static boolean role;

    public static String ip;

    private Button okButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        role = false;
        setContentView(R.layout.activity_main_menu_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        // Get Views from the MainMenu xml:
        Button host = findViewById(R.id.host_button);
        Button join = findViewById(R.id.join_button);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);


        //---HOST BUTTON---  (Everything that happens when host button is clicked)
        host.setOnClickListener(view -> {
            role = true;
            showServerPopUpWindow();
        });


        //---JOIN BUTTON---  (Everything that happens when join button is clicked)
        join.setOnClickListener(view -> {
            role=false;
            Intent intent = new Intent(getApplicationContext(), FindHostViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        MainActivity.subscribeToLogic(Constants.MAINMENU_ACTIVITY_TYPE, this);

        if (Config.Skip && Config.DEBUG) host.performClick();
    }

    public void establishServerConnection() throws InterruptedException {

        ClientLogic.isTEST = false;

        ServerNetworkClient server = new ServerNetworkClient(this.getApplicationContext());

        server.start();

        Thread.sleep(100);
        NetworkClientConnection client = new NetworkClientConnection("localhost", server.getPort(), 1000, logic);
        client.start();
        Thread.sleep(100);
        ServerActionHandler.setServer(server);
    }


    //---------------------------ALL METHODS:---------------------------//


    // This method will show the User a popUP Window to start a new Host Game.
    // When clicked on OK, it will proceed with "Start Server" method.
    public void showServerPopUpWindow() {

        // Everything that is needed to create the popUp Window:
        ConstraintLayout popUpConstraintLayout = findViewById(R.id.popUpConstraintLayout);
        View view = LayoutInflater.from(MainMenuActivity.this).inflate(R.layout.server_name_pop_up_window, popUpConstraintLayout);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();


        // Getting all needed Views from the xml:
        okButton = view.findViewById(R.id.okButton);
        enableOkButton();
        Button cancelButton = view.findViewById(R.id.cancelButton);
        EditText editText = view.findViewById(R.id.popUpEditText);
        EditText gameRoundsAndTime = view.findViewById(R.id.roundAndTimeEdtxt);
        EditText maxPlayersEDT = view.findViewById(R.id.maxPLayersEdTxt);
        RadioButton roundsButton = view.findViewById(R.id.roundsButton);
        RadioButton timeButton = view.findViewById(R.id.timeButton);


        // Check which Radio Button for Round Game or Time Game is selected:
        AtomicBoolean isRoundsSelected = new AtomicBoolean(true);
        AtomicBoolean isTimeSelected = new AtomicBoolean(false);

        roundsButton.setOnClickListener(v -> {
            isRoundsSelected.set(true);
            isTimeSelected.set(false);
        });

        timeButton.setOnClickListener(v -> {
            isRoundsSelected.set(false);
            isTimeSelected.set(true);
        });


        // When the OK Button is pressed, this is happening: ->
        okButton.setOnClickListener(view1 -> {



            // Creating temp Strings for checking inputs:
            String serverName = editText.getText().toString();
            String tempGameRoundsOrTime = gameRoundsAndTime.getText().toString();
            String tempMaxPlayers = maxPlayersEDT.getText().toString();

            // Check if TextViews are empty and valid:
            if (serverName.isEmpty()) {
                Toast.makeText(MainMenuActivity.this, "Please enter Servername", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tempMaxPlayers.isEmpty()) {
                Toast.makeText(MainMenuActivity.this, "Please enter Max Players (1-6)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!roundsButton.isChecked() && !timeButton.isChecked()) {
                Toast.makeText(MainMenuActivity.this, "Please choose Round or Time Game", Toast.LENGTH_SHORT).show();
                return;
            }

            if (tempGameRoundsOrTime.isEmpty()) {
                Toast.makeText(MainMenuActivity.this, "Please enter Rounds or Time (Minutes)", Toast.LENGTH_SHORT).show();
                return;
            }

            if(tempMaxPlayers.length() > 1){
                SnackBarHandler.createSnackbar(view, "Please enter a number between 1 and 6", LENGTH_SHORT).show();
                return;
            }

            if(tempGameRoundsOrTime.length() > 9 ){
                String type = roundsButton.isChecked() ? "Rounds" : "Time";
                SnackBarHandler.createSnackbar(view, "Please enter a valid amount for the total amount of "+type, LENGTH_SHORT).show();
                return;
            }

            int timeOrRounds = Integer.parseInt(tempGameRoundsOrTime);
            int maxPlayers = Integer.parseInt(tempMaxPlayers);

            if (!isValidMaxPlayers(maxPlayers)) {
                Toast.makeText(MainMenuActivity.this, "Choose players between 1 - 6", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidTimeOrRounds(timeOrRounds)) {
                Toast.makeText(MainMenuActivity.this, "Time/Rounds <= 0 not allowed", Toast.LENGTH_SHORT).show();
                return;
            }

            disableOkButton();

            try {
                Config.MAX_CLIENTS = maxPlayers;
                if (isRoundsSelected.get()) {
                    Config.ENDROUNDS = timeOrRounds;
                }
                if (isTimeSelected.get()) {
                    Config.END_TIME = timeOrRounds * 60000;
                }
                startServer(serverName);
            } catch (InterruptedException e) {
                Log.w("Warning", "Interrupted!", e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }
        });

        //When the CANCEL Button is pressed, this is happening: ->
        cancelButton.setOnClickListener(view1 -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();

        if (Config.Skip && Config.DEBUG) {
            editText.setText(R.string.cheat_user_element_username_placeholder);
            gameRoundsAndTime.setText("6");
            maxPlayersEDT.setText("6");
            roundsButton.setChecked(true);
            okButton.performClick();
        }
    }

    private void enableOkButton() {
        okButton.setEnabled(true);
        okButton.setBackgroundResource(R.drawable.host_btn_background);
    }

    private void disableOkButton() {
        okButton.setBackgroundResource(R.drawable.host_btn_background_disabled);
        okButton.setEnabled(false);
    }


    // Check if valid Methods:
    private boolean isValidMaxPlayers(int maxPlayers) {
        return maxPlayers >= Config.MIN_CLIENTS && maxPlayers <= Config.MAX_CLIENTS;
    }

    private boolean isValidTimeOrRounds(int timeOrRounds) {
        return timeOrRounds > 0;
    }


    // This Method will start with the Server and trigger the Action "HOST_NEW_GAME"
    public void startServer(String serverName) throws InterruptedException {
        MainActivity.subscribeToLogic(Constants.PREFIX_SERVER, this);
        server = new ServerNetworkClient(this.getApplicationContext());
        server.start();

        Thread.sleep(100);

        client = new NetworkClientConnection("localhost", server.getPort(), 1000, logic);
        ServerActionHandler.setServer(server);
        client.start();



        ClientHandler.setClient(client);

        Toast.makeText(MainMenuActivity.this, "Server " + serverName + " started on " + getTime(), Toast.LENGTH_SHORT).show();

        Thread.sleep(100);

        ServerActionHandler.triggerAction(PREFIX_HOST_NEW_GAME, user);
    }


    // This method returns the current time
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

}




