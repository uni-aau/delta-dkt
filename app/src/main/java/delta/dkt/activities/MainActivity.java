package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import delta.dkt.R;
import network.Client;
import ClientUIHandling.ClientHandler;
import ClientUIHandling.ClientLogic;
import network.TestServer;

public class MainActivity extends AppCompatActivity {

    private static ClientLogic logic;

    static TestServer server;

    static Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Force landscape screen at activity level


        establishServerConnection();

        //logic.setTextOfUIElement();

    }

    public static ClientLogic getLogic(){
        return logic;
    }

    public void establishServerConnection(){
        logic = new ClientLogic(new ClientHandler(findViewById(R.id.testView)));
        server = new TestServer(1024);
        client = new Client("localhost", 1024, logic);
        server.start();

        client.start();
        server.insertIntoOutputBuffer(ClientHandler.testType);


    }

}