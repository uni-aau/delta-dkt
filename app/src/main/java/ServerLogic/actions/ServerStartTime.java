package ServerLogic.actions;

import static ClientUIHandling.Constants.GameViewActivityType;
import static ClientUIHandling.Constants.PREFIX_GET_SERVER_TIME;

import android.os.CountDownTimer;
import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class ServerStartTime implements ServerActionInterface {
    private long elapsedTime = 0L;
    private int previousMinute = -1;
    private CountDownTimer timer;

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server Start Time", "Received Server Start Time request!");

        int endValue = 30000;
        timer = new CountDownTimer(endValue, 1000) {
            @Override
            public void onTick(long l) {
                elapsedTime += 1000; // + 1000 millisekunden -> +1 sekunde
                int currentMinute = (int) (elapsedTime / 60000);
                System.out.println("Debug - CurrentMinute = " + currentMinute);

//                if(currentMinute != previousMinute) { // todo nur eine request pro minute?
                server.broadcast(GameViewActivityType, PREFIX_GET_SERVER_TIME, new String[]{String.valueOf(elapsedTime)});
                previousMinute = currentMinute;
//                }
            }

            @Override
            public void onFinish() {
                timer.cancel();
                // max timer - Game schließt? Todo für spätere implementierung
            }
        };
        timer.start();
    }
}