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
    public static ArrayList<PropertyListElement> propertyElements = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        findViewById(R.id.button_backToGame).setOnClickListener(view -> switchToGameActivity());
        sendDataToRecyclerView();

        MainActivity.subscribeToLogic(Constants.PROPERTYLIST_ACTIVITY_TYPE, this);
    }

    protected void sendDataToRecyclerView() {
        PropertyListAdapter viewAdapter = new PropertyListAdapter(propertyElements, this);
        RecyclerView.LayoutManager viewManager = new LinearLayoutManager(this);

        RecyclerView recView = findViewById(R.id.recyclerViewPropList);
        recView.setLayoutManager(viewManager);
        recView.setAdapter(viewAdapter);
    }

    protected void switchToGameActivity() {
        finish();
    }
}