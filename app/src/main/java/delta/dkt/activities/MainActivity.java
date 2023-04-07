package delta.dkt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import delta.dkt.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtxt = findViewById(R.id.username_edittext);
        Button enter = findViewById(R.id.enter_btn);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                String username = edtxt.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter Username", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Welcome " + username + "!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });


    }


}