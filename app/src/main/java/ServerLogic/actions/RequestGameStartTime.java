package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.os.CountDownTimer;
import android.util.Log;

import ClientUIHandling.Config;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestGameStartTime implements ServerActionInterface {
    private long elapsedTime = 0L;
    private int previousMinute = -1;
    private CountDownTimer timer;

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server Start Time", "Received Server Start Time request!");

        timer = new CountDownTimer(Config.END_TIME, 1000) {
            @Override
            public void onTick(long l) {
                elapsedTime += 1000; // + 1000 millisekunden pro Tick -> +1 sekunde

                int currentMinute = (int) (elapsedTime / 60000);
                Log.d("[Server] Request Time", "CurrentMinute = " + currentMinute + "PreviousMinute = " + previousMinute);

                // if(currentMinute != previousMinute) { // Todo - Check, ob nur eine Request pro Minute?
                server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_GET_SERVER_TIME, new String[]{String.valueOf(elapsedTime)});
                previousMinute = currentMinute;
            }

            @Override
            public void onFinish() {
                timer.cancel();
                // Todo - Game wird abgebrochen und ein Gewinner eruiert - CountDown oder StoppUhr
            }
        };
        timer.start();
    }
}