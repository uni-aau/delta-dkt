package delta.dkt.activities;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import ClientUIHandling.Constants;
import delta.dkt.R;
import network2.NetworkServiceDiscovery;
import network2.ServerNetworkClient;


public class MainMenuActivity extends AppCompatActivity {

    ServerNetworkClient server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level


        Button host = findViewById(R.id.host_button);
        Button join = findViewById(R.id.join_button);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);




        host.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            // ---Start Server---
            server = new ServerNetworkClient();
            startServer(server);
            //Toast.makeText(MainMenuActivity.this, "Server started on "+getTime(), Toast.LENGTH_SHORT).show();
            //-----------------

            startActivity(intent);
        });

        join.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        MainActivity.subscribeToLogic(Constants.MainMenuActivityType, this);

    }

    public void startServer(ServerNetworkClient server){
        server.start();
    }

}




