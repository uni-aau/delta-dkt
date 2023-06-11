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
    private long elapsedTime = 0L;
    private int previousMinute = -1;
    private CountDownTimer timer;

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server Start Time", "Received Server Start Time request!");
        int clientID = (int) parameters;

        // Only first person starts timer (will be recoded in next sprint)
        if (clientID == 1) {
            if (timer != null) {
                timer.cancel(); // Cancel previous timer if it exists
            }
            elapsedTime = 0;
            previousMinute = -1;
            timer = new CountDownTimer(Config.END_TIME, 1000) {
                @Override
                public void onTick(long l) {
                    if(!server.isAlive()){
                        timer.cancel();
                    }

                    elapsedTime += 1000; // + 1000 millisekunden pro Tick -> +1 sekunde

                    int currentMinute = (int) (elapsedTime / 60000);
                    Log.d("[Server] Request Time", "CurrentMinute = " + currentMinute + "PreviousMinute = " + previousMinute);

                    // if(currentMinute != previousMinute) { // Todo - Check, ob nur eine Request pro Minute?
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_GET_SERVER_TIME, new String[]{String.valueOf(elapsedTime)});
                    previousMinute = currentMinute;

                }

                @Override
                public void onFinish() {
                    ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "TIME RAN OUT");
                    timer.cancel();
                }
            };
            timer.start();
        }
    }
}