package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        sendDataToFragment(); // Gets replaced later with server
    }

    protected void sendDataToFragment() {
        BlockFragment f1 = BlockFragment.newInstance("Fragment1", "1");

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerView, f1).commit();
    }
}