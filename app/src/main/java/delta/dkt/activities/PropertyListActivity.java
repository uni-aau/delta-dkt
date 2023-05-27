package delta.dkt.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.Constants;
import delta.dkt.R;
import delta.dkt.logic.structure.PropertyListElement;

public class PropertyListActivity extends AppCompatActivity {
    public static ArrayList<PropertyListElement> messages;
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
//        ArrayList<PropertyListElement> messages = new ArrayList<>();
        messages.add(new PropertyListElement("1", "Test1", "10", "50", "Test4", 4));

        PropertyListAdapter viewAdapter = new PropertyListAdapter(messages, this);
        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this);

        RecyclerView recView = findViewById(R.id.recyclerViewPropList);
        recView.setLayoutManager(viewManager);
        recView.setAdapter(viewAdapter);
    }

    protected void switchToGameActivity() {
        finish();
    }
}