package ServerLogic.actions;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class GameEnd implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        //TODO: Evaluate which player wins/loses. For example: most wealth, most properties, etc.

        // winnerList gets the values from the Hashmap
        ArrayList<Player> winners = new ArrayList<>(Game.getPlayers().values());
        ArrayList<String> args = new ArrayList<>();

        for(Player p : winners){
            args.add(String.format("%s#!#%d", p.getNickname(), p.getWealth()));
        }

        Log.d("[SERVER]_GAME_END", "JUHU! Game has ended// Name: "  + " "+ args);

                    // Sort the playerList by a specific attribute (e.g., wealth)
        //Collections.sort(winnerList, Comparator.comparingInt(Player::getWealth).reversed());


        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE,Constants.PREFIX_END_GAME, args.toArray(new String[args.size()]));


        try {
            //Wait for the message to be sent, then close the server
            Thread.sleep(100);
            server.tearDown();
            ServerActionHandler.serverUserList.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (InterruptedException e) {
            Log.w("Warning", "Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }

    }

}
