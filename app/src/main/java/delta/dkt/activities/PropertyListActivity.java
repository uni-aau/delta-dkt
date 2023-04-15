package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import delta.dkt.fragments.BlockFragment;
import delta.dkt.R;

public class PropertyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Force landscape screen at activity level

        findViewById(R.id.button_backToGame).setOnClickListener(view -> switchToGameActivity());
        sendDataToFragment(); // Gets replaced later with server
    }


    // Will be moved to server in the next sprint - Only test values
    protected void sendDataToFragment() {
        BlockFragment blockNumber1 = BlockFragment.newInstance("1", "Testprop", 5, "Player1", 1);
        BlockFragment blockNumber2 = BlockFragment.newInstance("2", "Testprop2", 10, "Player2", 2);
        BlockFragment blockNumber3 = BlockFragment.newInstance("3", "Testprop3", 100, "Player3", 3);
        BlockFragment blockNumber4 = BlockFragment.newInstance("4", "Testprop4", 5, "Player4", 4);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutImg1, blockNumber1).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutImg2, blockNumber2).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutImg3, blockNumber3).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayoutImg4, blockNumber4).commit();
    }

    protected void switchToGameActivity() {
        finish();
    }
}