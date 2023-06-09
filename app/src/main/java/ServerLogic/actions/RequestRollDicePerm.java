package ServerLogic.actions;

import static ClientUIHandling.Config.DEBUG;
import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PING;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_TIMEOUT_WARNING;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_REQUEST;

import ClientUIHandling.Constants;
import android.util.Log;

import ClientUIHandling.Config;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestRollDicePerm implements ServerActionInterface {

    private TimeOutThread timeOutThread;
    private static final String TAG = "[Server] Roll Dice Request";

    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = parameters.toString().trim().split(";");

        int nextClient;
        int oldClientId = Integer.parseInt(args[0]);
        Log.d(TAG, "Received next client request - Old client: " + oldClientId);

        if (DEBUG) {
            ServerActionHandler.triggerAction(PREFIX_PLAYER_MOVE, parameters);
            return;
        }

        // Check if clientID is last ID in game
        int size = Game.getPlayers().size();
        if (size != 0) {
            nextClient = getNextPlayerID(oldClientId);

            String nickName = Game.getPlayers().get(nextClient).getNickname();
            Log.d(TAG, "New roll dice server request - prevClientID " + oldClientId + " nextClient " + nextClient + " nickName " + nickName);

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
            if(Game.hasGameEnded()) ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "ROUNDS RAN OUT");

        } else {
            Log.e(TAG, "Error - No players available in GameView");
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
                        Log.e(TAG, "Interrupted!" + e);

                        Thread.currentThread().interrupt();
                    }
                }
                //Check if the player has disconnected
                Log.i("PING", ""+playerID);
                if(this.playerID != 1) {
                    ServerActionHandler.triggerAction(PING, playerID);
                }

                synchronized (synchTimeoutToken) {
                    int nextPlayerID = getNextPlayerID(playerID);
                    server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ROLL_DICE_REQUEST, new String[]{"" + nextPlayerID, Game.getPlayers().get(nextPlayerID).getNickname()});
                    Game.incrementRounds(playerID);
                    if(Game.hasGameEnded()) ServerActionHandler.triggerAction(Constants.PREFIX_END_GAME, "ROUNDS RAN OUT");
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

    private int getNextPlayerID(int oldClientId) {
        int nextCopy = oldClientId;
        int nextClient;
        Player player;

        do {
            nextClient = getNextClientId(nextCopy);
            player = getNextPlayer(nextClient);
            nextCopy = nextClient;
            Log.d(TAG, "Trying to get new clientID - nextClient = " + nextClient);
        } while(player == null);

        return nextClient;
    }

    private int getNextClientId(int clientId) {
        int size = Config.MAX_CLIENTS; // starts at 1 when all clients are checked

        if (clientId % size == 0) {
            return 1; // swap to first player
        }
        return clientId + 1;
    }

    private Player getNextPlayer(int nextClient) {
        return Game.getPlayers().get(nextClient);
    }
}