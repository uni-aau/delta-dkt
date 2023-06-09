package ServerLogic.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;

import android.util.Log;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestPing implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        int id = (int) parameters;
        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PING, new String[]{"" + id,"0"});
        new timeoutPingThread(Config.PING_TIMEOUT, id).start();
    }

    private class timeoutPingThread extends Thread {
        private int timeout;

        private int id;

        public timeoutPingThread(int timeout, int id) {
            this.timeout = timeout;

            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(timeout);
                if (!Game.getPlayers().get(id).getAndClearPing()) {
                    ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(id), "true", "player_lost_timeout_activity_text"}); // true -> leave event
                }
            } catch (InterruptedException e) {
                Log.e("TIMEOUT", "Interrupted!" + e);

                Thread.currentThread().interrupt();
            }
        }
    }
}
