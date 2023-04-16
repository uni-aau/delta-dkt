package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        try{
            establishServerConnection();
        }catch (Exception e){
            Log.d("MainActivity",e.getMessage());
        }


    }

    public void establishServerConnection() throws InterruptedException, RuntimeException {
        logic = new ClientLogic(new ClientHandler(findViewById(R.id.username_edittext)));
        ClientLogic.isTEST = false;
        ServerNetworkClient server = null;

            server = new ServerNetworkClient(this.getApplicationContext());
            server.start();

            Thread.sleep(1000);

            NetworkClientConnection client = new NetworkClientConnection("localhost", server.getPort(), 1000,logic );
            client.start();
            Thread.sleep(1000);

            server.broadcast(ClientHandler.testType);
    }





}