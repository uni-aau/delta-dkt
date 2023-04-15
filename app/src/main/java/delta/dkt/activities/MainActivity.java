package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.ServerSocket;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import delta.dkt.R;
import network2.NetworkClientConnection;
import network2.ServerNetworkClient;

public class MainActivity extends AppCompatActivity {

    public static final String INTENT_PARAMETER = "username";

    private static ClientLogic logic;

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
            if(username.isEmpty()){
                Toast.makeText(MainActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                intent.putExtra(INTENT_PARAMETER, username);
                startActivity(intent);
            }
        });
        establishServerConnection();

    }

    public void establishServerConnection(){
        logic = new ClientLogic(new ClientHandler(findViewById(R.id.username_edittext)));
        ClientLogic.isTEST = false;
        ServerNetworkClient server = null;

            server = new ServerNetworkClient(12312);
            NetworkClientConnection client = new NetworkClientConnection("localhost", 12312, 1000,logic );
            server.start();

            client.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Log.e("INTERRUPT", "Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        server.broadcast(ClientHandler.testType);







    }

}