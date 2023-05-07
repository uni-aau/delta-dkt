package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_GET_IP;
import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;
import static delta.dkt.activities.MainActivity.logic;
import static delta.dkt.activities.MainActivity.user;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        // Get Views from the MainMenu xml:
        Button host = findViewById(R.id.host_button);
        Button join = findViewById(R.id.join_button);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);


        //---HOST BUTTON---  (Everything that happens when host button is clicked)
        host.setOnClickListener(view -> {
           /* try {
                establishServerConnection();
            } catch (InterruptedException e) {
                Log.d("MainActivity::oncreate- interrupted", e.getMessage());
                Thread.currentThread().interrupt();
            } catch (RuntimeException e) {
                Log.d("MainActivity::oncreate - Runtime exception", e.getMessage());
            }*/
            role = true;
            showServerPopUpWindow();
        });


        //---JOIN BUTTON---  (Everything that happens when join button is clicked)
        join.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FindHostViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        MainActivity.subscribeToLogic(Constants.MAINMENU_ACTIVITY_TYPE, this);
    }

    public void establishServerConnection() throws InterruptedException, RuntimeException {

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


    // This method will show the User a popUP Window to enter a Server name.
    // When clicked on OK, it will proceed with "Start Server" method.
    public void showServerPopUpWindow() {

        ConstraintLayout popUpConstraintLayout = findViewById(R.id.popUpConstraintLayout);
        View view = LayoutInflater.from(MainMenuActivity.this).inflate(R.layout.server_name_pop_up_window, popUpConstraintLayout);

        Button okButton = view.findViewById(R.id.okButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        EditText editText = view.findViewById(R.id.popUpEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        okButton.setOnClickListener(view1 -> {
            String serverName = editText.getText().toString();
            if (serverName.isEmpty()) {
                Toast.makeText(MainMenuActivity.this, "Please enter Servername", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    startServer(serverName);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelButton.setOnClickListener(view1 -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }


    // This Method will start with the Server and trigger the Action "HOST_NEW_GAME"
    public void startServer(String serverName) throws InterruptedException, RuntimeException {
        MainActivity.subscribeToLogic(Constants.PREFIX_SERVER, this);
        server = new ServerNetworkClient(this.getApplicationContext());
        server.start();
        Thread.sleep(100);

        client = new NetworkClientConnection("localhost", server.getPort(), 1000, logic);
        ServerActionHandler.setServer(server);
        client.start();
        Thread.sleep(100);

        ClientHandler.setClient(client);

        Toast.makeText(MainMenuActivity.this, "Server " + serverName + " started on " + getTime(), Toast.LENGTH_SHORT).show();



        ServerActionHandler.triggerAction(PREFIX_HOST_NEW_GAME, user);




    }


    // This method returns the current time
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    // TO DO->
    /*
    public void closeServer() {
        try {
            server.tearDown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeClient() {
        try {
            client.stopConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
*/
}




