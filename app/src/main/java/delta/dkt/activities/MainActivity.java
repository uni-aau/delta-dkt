package delta.dkt.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;


import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import ClientUIHandling.Constants;
import delta.dkt.R;
import network2.NetworkClientConnection;
import network2.ServerNetworkClient;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_PARAMETER = "username";

    private static ClientLogic logic;

    static {
        HashMap<String, ClientHandler> handlers = new HashMap<>();
        logic = new ClientLogic(handlers);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        EditText edtxt = findViewById(R.id.username_edittext);
        Button enter = findViewById(R.id.enter_btn);

        enter.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
            String username = edtxt.getText().toString();
            if (username.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                intent.putExtra(INTENT_PARAMETER, username);
                startActivity(intent);
            }
        });
        subscribeToLogic(Constants.MainActivityType, this);
        try {
            establishServerConnection();
        } catch (InterruptedException e) {
            Log.d("MainActivity::oncreate- interrupted", e.getMessage());
            Thread.currentThread().interrupt();
        } catch (RuntimeException e) {
            Log.d("MainActivity::oncreate - Runtime exception", e.getMessage());
        }


    }


    public void establishServerConnection() throws InterruptedException, RuntimeException {

        ClientLogic.isTEST = false;


        ServerNetworkClient server = new ServerNetworkClient(this.getApplicationContext());

        server.start();

        Thread.sleep(100);
        NetworkClientConnection client = new NetworkClientConnection("localhost", server.getPort(), 1000, logic);
        client.start();
        Thread.sleep(100);

        server.broadcast(Constants.MainActivityType+":"+Constants.PREFIX_PLAYER_MOVE+" CHANGED NAME");

    }

    public static void subscribeToLogic(String type, AppCompatActivity activity) {
        logic.getHandler().put(type, new ClientHandler(activity));
    }

}