package delta.dkt.activities;


import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;
import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.LOG_CHEAT;
import static ClientUIHandling.Constants.PREFIX_CLOSE_GAME;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;
import static ClientUIHandling.Constants.PREFIX_INIT_PLAYERS;
import static ClientUIHandling.Constants.PREFIX_PLAYER_CHEAT_MENU;
import static ClientUIHandling.Constants.PREFIX_PLAYER_LEAVE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_REPORT_CHEATER;
import static ClientUIHandling.Constants.PREFIX_PROPLIST_UPDATE;
import static ClientUIHandling.Constants.PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_RECEIVE;
import static delta.dkt.R.id.imageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ClientUIHandling.ClientHandler;
import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ClientUIHandling.handlers.languages.LanguageHandler;
import ClientUIHandling.handlers.notifications.SnackBarHandler;
import ClientUIHandling.handlers.positioning.PositionHandler;
import ServerLogic.ServerActionHandler;
import delta.dkt.R;
import delta.dkt.sensors.LightSensor;


public class GameViewActivity extends AppCompatActivity {
    public static int clientID = -1; // ID gets set by server
    public static int players = -1; // players gets set by server
    public static boolean isDicing = false;
    private final int[] locations = {1, 1, 1, 1, 1, 1};
    private SensorManager manager = null;
    private final LightSensor lightSensorListener = new LightSensor();
    private Sensor lightSensor = null;
    public int cheatSelection = -1; //? represents the index / position of the player-element selected, in the report-menu.

    private Button btnDice;
    private ImageView map;


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
            ServerActionHandler.triggerAction(PREFIX_INIT_PLAYERS, 1); // Set player & handle dice perms
            ServerActionHandler.triggerAction(PREFIX_PROPLIST_UPDATE, 1); // initializes propertylist
        }

        Button btnReportCheat = findViewById(R.id.btnReportCheater);
        btnReportCheat.setOnClickListener(view -> {
            Log.v(LOG_CHEAT, "Report Menu is requested.");
            btnReportCheat.setEnabled(false);

            //? Request the usernames from the server to display them in the menu later on.
            ClientHandler.sendMessageToServer(GAMEVIEW_ACTIVITY_TYPE, PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT, new Object[]{PREFIX_PLAYER_CHEAT_MENU, String.valueOf(clientID)});
        });

        registerLightSensor();
        displayPlayers(players);
        handleMovementRequests();

        if (Config.Skip && Config.DEBUG) {
            btnPropertyInfos.performClick();
        }
    }

    // Action when player presses back on mobile phone
    @Override
    public void onBackPressed() {
        openPlayerLeavePopUp();
    }

    @SuppressLint("SetTextI18n")
    private void openPlayerLeavePopUp() {
        ConstraintLayout popUpConstraintLayout = findViewById(R.id.playerLeavePopUpConstraint);
        View view = LayoutInflater.from(this).inflate(R.layout.player_leave_pop_up_window, popUpConstraintLayout);

        Button leaveGame = view.findViewById(R.id.button_leaveGame_yes);
        Button cancelLeaveGame = view.findViewById(R.id.button_leaveGame_no);
        TextView playerLeaveHint = view.findViewById(R.id.textView_playerLeaveHint);

        final AlertDialog alertDialog = createAlertDialog(view);

        cancelLeaveGame.setOnClickListener(view1 -> alertDialog.dismiss());
        playerLeaveHint.setVisibility(View.VISIBLE);
        if (!isDicing || players == 1) {
            if (MainMenuActivity.role) {
                playerLeaveHint.setText(R.string.text_player_leave_hint_host);
                leaveGame.setOnClickListener(view1 -> ServerActionHandler.triggerAction(PREFIX_CLOSE_GAME, ""));
            } else
                leaveGame.setOnClickListener(view1 -> ClientHandler.sendMessageToServer(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_LEAVE, String.valueOf(clientID)));
        } else {
            leaveGame.setOnClickListener(view1 -> {
                Toast.makeText(this, "You cannot leave since you need to dice!", Toast.LENGTH_SHORT).show(); // TODO can also be shown via TextView
                alertDialog.dismiss();
            });
        }

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
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
        isDicing = true;
    }

    public void disableDice() {
        btnDice.setEnabled(false);
        btnDice.setBackgroundResource(R.drawable.host_btn_background_disabled);
        map.setEnabled(false); // prevent touch event
        isDicing = false;
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

    @SuppressLint("DefaultLocale")
    public void createSelectionPopup(ArrayList<String> playerNames) {
        ConstraintLayout popUpConstraintLayout = findViewById(R.id.cheatConstraint);
        View view = LayoutInflater.from(this).inflate(R.layout.report_cheat_popup, popUpConstraintLayout);

        if (playerNames.size() == 0) {
            Log.v(LOG_CHEAT, "No players found, using default names.");
            playerNames.add("Player1");
            playerNames.add("Player2");
            playerNames.add("Player3");
            playerNames.add("Player4");
            playerNames.add("Player5");
            playerNames.add("Player6");
        }
        RecyclerView recyclerView = view.findViewById(R.id.rececylerCheatPlayer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CheatUserAdapter adapter = new CheatUserAdapter(this, playerNames);
        recyclerView.setAdapter(adapter);

        Button submitCheater = view.findViewById(R.id.btnSubmitCheater);
        Button cancelCheater = view.findViewById(R.id.btnCancelCheater);

        final AlertDialog alertDialog = createAlertDialog(view);

        submitCheater.setOnClickListener(view1 -> {
            if (this.cheatSelection < 0) {
                SnackBarHandler.createSnackbar(recyclerView, "Please select a player before reporting!", LENGTH_SHORT, true).show();
                return;
            }

            Log.d(LOG_CHEAT, "A player has been reported as a cheater! => id=" + (this.cheatSelection + 1));
            LanguageHandler.updateTextElement(this, "textView_activity", "reportCheater_message", new Object[]{playerNames.get(cheatSelection)});
            ClientHandler.sendMessageToServer(GAMEVIEW_ACTIVITY_TYPE, PREFIX_REQUEST_SERVER_ACTION_AS_CLIENT, new Object[]{PREFIX_PLAYER_REPORT_CHEATER, String.valueOf(clientID), String.valueOf(this.cheatSelection + 1)});
            alertDialog.dismiss();
        });

        cancelCheater.setOnClickListener(view1 -> alertDialog.dismiss());
        alertDialog.setOnDismissListener(dialogInterface -> findViewById(R.id.btnReportCheater).setEnabled(true));

        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

    private AlertDialog createAlertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        return builder.create();
    }
}