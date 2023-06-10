package ServerLogic.actions;

import static ClientUIHandling.Config.DEBUG;
import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PING;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_TIMEOUT_WARNING;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import android.util.Log;

import ClientUIHandling.Config;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {

    private TimeOutThread timeOutThread;

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = parameters.toString().trim().split(";");

        int nextClient;
        String tag = "[Server] Roll Dice Request";
        int oldClientId = Integer.parseInt(args[0]);
        Log.d(tag, "Received next client request - Old client: " + parameters);

        if (DEBUG) {
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);
            return;
        }

        // Check if clientID is last ID in game
        int size = Game.getPlayers().size();
        if (size != 0) {
            nextClient = getNextPlayerID(oldClientId, size);

            String nickName = Game.getPlayers().get(nextClient).getNickname();
            Log.d("[SERVER]", "New roll dice server request - prevClientID " + oldClientId + " nextClient " + nextClient + " nickName " + nickName);

            Log.d(tag, "OldClientId = " + oldClientId + " NewClient = " + nextClient);



            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{String.valueOf(nextClient), nickName});
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);

            //If this is the first dice request, start the timeout loop, else reset the values.
            if (timeOutThread == null) {
                createAndStartTimeout(nextClient, server);
            }else{
                if(timeOutThread.isAlive()) {
                    timeOutThread.resetTimeout(nextClient);
                }else{
                    createAndStartTimeout(nextClient, server);
                }
            }

            Game.incrementRounds(oldClientId);

        } else {
            Log.e(tag, "Error - No players available in GameView");
        }
    }

    private void createAndStartTimeout(int nextClient, ServerNetworkClient server){
        timeOutThread = new TimeOutThread(Config.TIMEOUT, nextClient, server);
        timeOutThread.start();
    }

    private class TimeOutThread extends Thread {

        private int timeout;

        private int playerID;

        private boolean isRunning;

        private ServerNetworkClient server;

        private Object synchRunnToken;

        private Object synchTimeoutToken;

        private long startTime;

        private boolean clientHasBeenWarned;

        public TimeOutThread(int timeout, int playerID, ServerNetworkClient server) {
            this.timeout = timeout;
            this.playerID = playerID;
            this.server = server;
            synchRunnToken = "";
            synchTimeoutToken = "";

        }

        @Override
        public void run() {
            synchronized (synchRunnToken) {
                this.isRunning = true;
            }

            while (true) {
                resetTimeout(playerID);

                while (true) {
                    synchronized (synchRunnToken) {
                        if (!isRunning || server.hasClosed()) {
                            Log.i("TIMEOUT", "SERVER CLOSED, SHUTTING DOWN TIMEOUT");
                            return;
                        }
                        synchronized (synchTimeoutToken) {
                            if (System.currentTimeMillis() - startTime >= timeout) {
                                break;
                            }
                            if(!clientHasBeenWarned) {
                                long remainingTime = timeout - (System.currentTimeMillis() - startTime);
                                if (remainingTime <= Config.TIMEOUT_WARNING_THRESHOLD) {
                                    Log.i("TIMEOUT", "SEND WARNING");
                                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_TIMEOUT_WARNING, new String[]{"" + playerID});
                                    clientHasBeenWarned = true;
                                }

                            }
                        }


                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Log.e("ROLLDICE", "Interrupted!" + e);

                        Thread.currentThread().interrupt();
                    }
                }
                //Check if the player has disconnected
                Log.i("PING", ""+playerID);
                if(this.playerID != 1) {
                    ServerActionHandler.triggerAction(PING, playerID);
                }

                synchronized (synchTimeoutToken) {
                    int nextPlayerID = getNextPlayerID(playerID, Game.getPlayers().size());
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{"" + nextPlayerID, Game.getPlayers().get(nextPlayerID).getNickname()});
                    Game.incrementRounds(playerID);
                    this.playerID = nextPlayerID;
                }


            }

        }

        public void stopTimeout() {
            synchronized (synchRunnToken) {
                this.isRunning = false;
            }
        }

        public void resetTimeout(int playerID) {
            synchronized (synchTimeoutToken) {
                this.startTime = System.currentTimeMillis();
                this.playerID = playerID;
                this.clientHasBeenWarned = false;
            }
        }

    }

    private int getNextPlayerID(int oldClientId, int size) {
        if (oldClientId % size == 0) {
            return 1; // swap to first player
        }
        return oldClientId + 1;

    }
}