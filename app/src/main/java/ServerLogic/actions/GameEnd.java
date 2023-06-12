package ServerLogic.actions;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class GameEnd implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("[SERVER] GameEnd", "Received game end request with reason: " + (String) parameters);

        // winnerList gets the values from the Hashmap
        ArrayList<Player> winners = new ArrayList<>(Game.getWinnerList());
        ArrayList<String> args = new ArrayList<>();

        for(Player p : winners){
            args.add(String.format(Locale.getDefault(),"%s#!#%d", p.getNickname(), p.getWealth()));
        }

        Log.d("[SERVER]_GAME_END", "Before Teardown");


        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE,Constants.PREFIX_END_GAME, args.toArray(new String[0]));


        try {
            //Wait for the message to be sent, then close the server
            Thread.sleep(100);
            server.tearDown();
            Log.d("[SERVER]_GAME_END", "After Teardown");
            Game.getPlayers().clear();
        } catch (InterruptedException e) {
            Log.w("Warning", "Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new RuntimeException("Error while trying to close the server: " + e);
        }

    }

}
