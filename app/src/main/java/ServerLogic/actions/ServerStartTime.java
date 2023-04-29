package ServerLogic.actions;

import static ClientUIHandling.Constants.LobbyViewActivityType;
import static ClientUIHandling.Constants.PREFIX_GAME_START;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.os.CountDownTimer;
import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class ServerStartTime implements ServerActionInterface {
    private long elapsedTime = 0L;

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server Start Time", "Received Server Start Time request!");

        int endValue = 300000;

        CountDownTimer countDownTimer = new CountDownTimer(endValue, 1000) {
            @Override
            public void onTick(long l) {
                elapsedTime += 1000;
                server.broadcast(LobbyViewActivityType, PREFIX_GET_SERVER_TIME, new String[]{String.valueOf(elapsedTime)});
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }
}
