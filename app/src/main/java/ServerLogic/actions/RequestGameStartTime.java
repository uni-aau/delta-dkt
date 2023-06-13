package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.os.CountDownTimer;
import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestGameStartTime implements ServerActionInterface {
    private static final String TAG = "[Server] Start Time";
    private long elapsedTime = 0L;
    private int previousMinute = -1;
    private CountDownTimer timer;
    private ServerNetworkClient server;

    @Override
    public void execute(ServerNetworkClient serverNetworkClient, Object parameters) {
        Log.d(TAG, "Received Server Start Time request!");
        int clientID = (int) parameters;
        server = serverNetworkClient;

        if (clientID == 1) {
            initializeTimer();
        }
    }

    private void initializeTimer() {
        cancelPreviousTimer();

        timer = new CountDownTimer(Config.END_TIME, 1000) {
            @Override
            public void onTick(long l) {
                cancelTimerIfServerIsNotAlive();

                elapsedTime += 1000; // + 1000 millisekunden pro Tick -> +1 sekunde

                int currentMinute = (int) (elapsedTime / 60000);
                if (currentMinute != previousMinute) {
                    Log.d(TAG, "New minute! Previous minute " + previousMinute + " new minute " + currentMinute);
                }

                server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_GET_SERVER_TIME, new String[]{String.valueOf(elapsedTime)});
                previousMinute = currentMinute;
            }

            @Override
            public void onFinish() {
                if (!Config.SELECTED_GAME_MODE) { // Only stop the game via time when host selected max time as game mode
                    ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "TIME RAN OUT");
                    timer.cancel();
                }
            }
        };
        timer.start();

    }

    private void cancelPreviousTimer() {
        elapsedTime = 0;
        previousMinute = -1;

        if (timer != null) {
            timer.cancel();
        }
    }

    private void cancelTimerIfServerIsNotAlive() {
        if (!server.isAlive()) {
            timer.cancel();
        }
    }
}