package delta.dkt.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import delta.dkt.R;
import delta.dkt.fragments.BlockFragment;

public class PropertyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        findViewById(R.id.button_backToGame).setOnClickListener(view -> switchToGameActivity());
        sendDataToFragment(); // Gets replaced later with server
    }


    // Will be moved to server in the next sprint - Only test values
    protected void sendDataToFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        BlockFragment blockNumber1 = BlockFragment.newInstance("1", "Testprop", 5, "Player1", 1);
        BlockFragment blockNumber2 = BlockFragment.newInstance("2", "Testprop2", 10, "Player2", 2);
        BlockFragment blockNumber3 = BlockFragment.newInstance("3", "Testprop3", 100, "Player3", 3);
        BlockFragment blockNumber4 = BlockFragment.newInstance("4", "Testprop4", 5, "Player4", 4);

        transaction.add(R.id.linearLayout_Fragments, blockNumber1);
        transaction.add(R.id.linearLayout_Fragments, blockNumber2);
        transaction.add(R.id.linearLayout_Fragments, blockNumber3);
        transaction.add(R.id.linearLayout_Fragments, blockNumber4);

        transaction.commit();
    }

    protected void switchToGameActivity() {
        finish();
    }
}