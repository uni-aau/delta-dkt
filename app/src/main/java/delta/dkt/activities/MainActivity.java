package delta.dkt.activities;

import static delta.dkt.activities.LobbyViewActivity.userList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;


import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;
import network2.NetworkClientConnection;
import network2.ServerNetworkClient;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_PARAMETER = "username";

    public static ClientLogic logic;
    public static String user; // remove after static string username is moved in this class.

    static {
        HashMap<String, ClientHandler> handlers = new HashMap<>();
        logic = new ClientLogic(handlers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        // Get Views from the MainActivity xml:
        EditText edtxt = findViewById(R.id.username_edittext);
        Button enter = findViewById(R.id.enter_btn);

        subscribeToLogic(Constants.MAIN_ACTIVITY_TYPE, this);

        //---ENTER BUTTON---    (Everything that happens when ENTER Button is pressed)
        enter.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            user = edtxt.getText().toString();
            if (user.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
            } else if (checkIfUsernameAlreadyExists(user)){
                Toast.makeText(MainActivity.this, "This Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Welcome " + user + "!", Toast.LENGTH_SHORT).show();
                intent.putExtra(INTENT_PARAMETER, user);
                //MainMenuActivity.username = user;
                startActivity(intent);
            }
        });




    }


    //--------------------------ALL METHODS-----------------------------//




    public static void subscribeToLogic(String type, AppCompatActivity activity) {
        logic.getHandler().put(type, new ClientHandler(activity));
    }


    public boolean checkIfUsernameAlreadyExists (String newUsername){
        return (userList.contains(newUsername));
    }


}