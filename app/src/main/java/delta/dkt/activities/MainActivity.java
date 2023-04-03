package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import delta.dkt.R;
import network.ClientHandler;
import network.ClientLogic;

public class MainActivity extends AppCompatActivity {

    private ClientLogic logic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Force landscape screen at activity level

        logic = new ClientLogic(new ClientHandler());
        //logic.setTextOfUIElement();

    }
}