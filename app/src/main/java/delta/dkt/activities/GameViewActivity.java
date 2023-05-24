package delta.dkt.activities;

import static ClientUIHandling.Constants.*;
import static delta.dkt.R.id.imageView;
import static delta.dkt.activities.MainMenuActivity.role;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ClientUIHandling.handlers.positioning.PositionHandler;
import ServerLogic.ServerActionHandler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import delta.dkt.R;
import delta.dkt.logic.structure.Game;
import delta.dkt.sensors.LightSensor;

import java.util.ArrayList;


public class GameViewActivity extends AppCompatActivity {
    public static int clientID = -1; // ID gets set by server
    public static int players = -1; // players gets set by server
    private int[] locations = {1, 1, 1, 1, 1, 1};
    private SensorManager manager = null;
    private LightSensor lightSensorListener = new LightSensor();
    private Sensor lightSensor = null;
    public int cheatSelection = -1;

    Button btnDice;
    ImageView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);
        Config.Skip = false;

        Button btnPropertyInfos = findViewById(R.id.button_property_infos);
        btnDice = findViewById(R.id.button_roll_dice);
        map = findViewById(imageView);
        btnPropertyInfos.setOnClickListener(view -> switchToPropertyActivity());

        MainActivity.subscribeToLogic(Constants.GAMEVIEW_ACTIVITY_TYPE, this);
        if (MainMenuActivity.role) {
            ServerActionHandler.triggerAction(PREFIX_GET_SERVER_TIME, clientID); // Get game time
            ServerActionHandler.triggerAction(PREFIX_INIT_PLAYERS, String.valueOf(clientID)); // Set player & handle dice perms
            ServerActionHandler.triggerAction(PREFIX_GAME_START_STATS, String.valueOf(Game.getPlayers().size())); // Update player stats
        }


        registerLightSensor();
        displayPlayers(players);
        handleMovementRequests();
        createSelectionPopup();

        if (Config.Skip && Config.DEBUG) {
            btnPropertyInfos.performClick();
        }
    }

    protected void switchToPropertyActivity() {
        Intent switchIntent = new Intent(this, PropertyListActivity.class);
        startActivity(switchIntent);
    }

    private void registerLightSensor() {
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (lightSensor == null) lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        manager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(lightSensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * This method handles the movement requests of a client, thus sending the request to server.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void handleMovementRequests() {
        //? Places all figures on their designated position inside the start field.
        for (int i = 0; i < locations.length; i++) {
            int index = i;
            map.post(() -> updatePlayerPosition(locations[index], index + 1));
        }

        map.post(() -> PositionHandler.setLogs(true));


        btnDice.setOnClickListener(view -> {
            Log.d("Movement", "Sending movement request to server!");
            ClientHandler.sendMessageToServer(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_RECEIVE, new Object[]{String.valueOf(clientID), String.valueOf(LightSensor.isCovered())});
        });


        map.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN) return false;

            btnDice.performClick();
            return true;
        });

    }

    /**
     * This method will set update the location of a players figure to the requested destination.
     *
     * @param destination The destinations number, from 1: Start to 39: Last field.
     * @param _player The player figure that is to be moved, ranging from 1 to 6.
     */
    public void updatePlayerPosition(int destination, int _player) {
        int figureIdentifier = getResources().getIdentifier("player" + _player, "id", getPackageName());
        ImageView figure = findViewById(figureIdentifier);

        if (map == null || figure == null) {
            Log.e("Movement-GameView", String.format("Aborting movement! Because %s", (map == null ? "the map has not been found!" : "the players figure couldn't be found!")));
            return;
        }

        var pos = PositionHandler.calculateFigurePosition(destination, _player, figure, map);
        figure.setX(pos.x);
        figure.setY(pos.y);
    }

    public void enableDice() {
        btnDice.setEnabled(true);
        btnDice.setBackgroundResource(R.drawable.host_btn_background);
        map.setEnabled(true);
    }

    public void disableDice() {
        btnDice.setEnabled(false);
        btnDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
        map.setEnabled(false); // prevent touch event
    }

    /**
     * This method will set the figure's for the request amount of players visible.
     *
     * @param count Amount of players of which the figures are going to be made visible.
     */
    public void displayPlayers(int count) {
        Log.d("[GameView] Action", "Enabling players " + count);

        if (count <= Config.MAX_CLIENTS) {
            for (int i = 1; i <= count; i++) {
                ImageView figure = findViewById(getResources().getIdentifier("player" + i, "id", getPackageName()));

                if (figure == null) continue;

                figure.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("[UI] Action Error", String.format("Error - Less player markers (%d) than players (%d)!", Config.MAX_CLIENTS, count));
            Toast.makeText(this, "There was an error while adding another player - Check error logs!", Toast.LENGTH_SHORT).show();
        }
    }

    public void createSelectionPopup() {
        ConstraintLayout popUpConstraintLayout = findViewById(R.id.cheatContrraint);
        View view = LayoutInflater.from(this).inflate(R.layout.report_cheat_popup, popUpConstraintLayout);


        RecyclerView recyclerView = view.findViewById(R.id.rececylerCheatPlayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> names = new ArrayList<>();
        names.add("Player1");
        names.add("Player2");
        names.add("Player3");
        names.add("Player4");
        names.add("Player5");
        names.add("Player6");
        CheatUserAdapter adapter = new CheatUserAdapter(this, names);
        recyclerView.setAdapter(adapter);

        Button submitCheater = view.findViewById(R.id.btnSubmitCheater);
        Button cancelCheater = view.findViewById(R.id.btnCancelCheater);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();

        submitCheater.setOnClickListener(view1 -> {

        });

        cancelCheater.setOnClickListener(view1 -> alertDialog.dismiss());

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}