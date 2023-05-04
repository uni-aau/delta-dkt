package delta.dkt.activities;

import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;
import static delta.dkt.activities.MainActivity.INTENT_PARAMETER;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;

import network2.ServerNetworkClient;


public class MainMenuActivity extends AppCompatActivity {

    ServerNetworkClient server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_view);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Force portrait screen at activity level

        // Get Views from the MainMenu xml:
        Button host = findViewById(R.id.host_button);
        Button join = findViewById(R.id.join_button);


        //---HOST BUTTON---  (Everything that happens when host button is clicked)
        host.setOnClickListener(view -> showServerPopUpWindow());


        //---JOIN BUTTON---  (Everything that happens when join button is clicked)
        join.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LobbyViewActivity.class);
            intent.putExtra(INTENT_PARAMETER, returnNewUser());
            startActivity(intent);
        });

        MainActivity.subscribeToLogic(Constants.MAINMENU_ACTIVITY_TYPE, this);
    }



    //---------------------------ALL METHODS:---------------------------//


    // This method will show the User a popUP Window to enter a Server name.
    // When clicked on OK, it will proceed with "Start Server" method.
    public void showServerPopUpWindow() {

        ConstraintLayout popUpConstraintLayout = findViewById(R.id.popUpConstraintLayout);
        View view = LayoutInflater.from(MainMenuActivity.this).inflate(R.layout.server_name_pop_up_window, popUpConstraintLayout);

        Button okButton = view.findViewById(R.id.okButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        EditText editText = view.findViewById(R.id.popUpEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        okButton.setOnClickListener(view1 -> {
            String serverName = editText.getText().toString();
            if (serverName.isEmpty()) {
                Toast.makeText(MainMenuActivity.this, "Please enter Servername", Toast.LENGTH_SHORT).show();
            }else {
                startServer(serverName);
            }
        });

        cancelButton.setOnClickListener(view1 -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }



    // This Method will start with the Server and trigger the Action "HOST_NEW_GAME"
    public void startServer(String serverName){
        server = new ServerNetworkClient();
        server.start();
        Toast.makeText(MainMenuActivity.this, "Server "+serverName+" started on "+getTime(), Toast.LENGTH_SHORT).show();
        ServerActionHandler.triggerAction(PREFIX_HOST_NEW_GAME, null);
    }



    // This method returns the current time
    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }


    // This method returns the New User name
    public String returnNewUser(){
        return getIntent().getStringExtra(INTENT_PARAMETER);
    }


}




