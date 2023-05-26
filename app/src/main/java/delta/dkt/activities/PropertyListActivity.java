package delta.dkt.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.Constants;
import delta.dkt.R;
import delta.dkt.fragments.BlockFragment;

public class PropertyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        findViewById(R.id.button_backToGame).setOnClickListener(view -> switchToGameActivity());
        sendDataToFragment(); // Gets replaced later with server

        MainActivity.subscribeToLogic(Constants.PROPERTYLIST_ACTIVITY_TYPE, this);
    }


    // Will be moved to server in the next sprint - Only test values
    protected void sendDataToFragment() {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("Test1");

        PropListAdapter viewAdapter = new PropListAdapter(messages);
        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this);

        RecyclerView recView = findViewById(R.id.recyclerViewPropList);
        recView.setLayoutManager(viewManager);
        recView.setAdapter(viewAdapter);
    }

    protected void switchToGameActivity() {
        finish();
    }
}