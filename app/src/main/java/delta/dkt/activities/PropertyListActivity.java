package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import delta.dkt.BlockFragment;
import delta.dkt.R;

public class PropertyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Force landscape screen at activity level

        getSupportActionBar().hide();
        System.out.println("DEBUG wie oft?");
        sendDataToFragment(); // Gets replaced later with server
    }

    protected void sendDataToFragment() {
        BlockFragment blockNumber1 = BlockFragment.newInstance("Fragment1", "1", "Testprop", 5, "Player1", 2);
//        BlockFragment blockNumber2 = BlockFragment.newInstance("Fragment2", "2", "Testprop2", 10, "Player2", 1);

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout1, blockNumber1).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView2, blockNumber2).commit();
    }
}