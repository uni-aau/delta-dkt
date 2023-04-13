package delta.dkt.activities;

import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import delta.dkt.R;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_view);


        Button host = findViewById(R.id.host_button);
        Button join = findViewById(R.id.join_button);
        String newUser = getIntent().getStringExtra(INTENT_PARAMETER);

        host.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

        join.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, newUser);
            startActivity(intent);
        });

    }
}


