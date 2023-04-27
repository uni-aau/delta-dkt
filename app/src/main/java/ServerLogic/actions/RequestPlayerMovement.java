package ServerLogic.actions;

import ServerLogic.ServerActionInterface;
import android.util.Log;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

import java.util.ArrayList;

import static ClientUIHandling.Constants.*;

@SuppressWarnings("FieldCanBeLocal")
public class RequestPlayerMovement implements ServerActionInterface {
    private String tag = "Movement-"+getClass().getSimpleName();
    private int maxFields = 39;


    @SuppressWarnings("DataFlowIssue")
    @Override
    public void execute (ServerNetworkClient server, Object parameters) {
        int clientID;
        //? Checking whether the id of the request-player is valid.
        try{
            clientID = Integer.parseInt(parameters.toString());
        }catch (NumberFormatException e){
            Log.e("Movement", "Parsing the clientsID failed, as its format is invalid!");
            e.printStackTrace();

            server.broadcast(GameViewActivityType, PREFIX_PLAYER_MOVE, new String[]{"error", "Someone tried to move but provided an invalid id."});
            return;
        }

        //? Player is not inside the players 'collection' of the game, thus cannot be worked with.
        if(!Game.getPlayers().containsKey(clientID)){
            Log.e(tag, String.format("A player with the id: %s requested to move, but is not yet registered on the server side. Aborting request!", clientID));
            server.broadcast(GameViewActivityType, PREFIX_PLAYER_MOVE, new String[]{"error", String.format("Player %s tried to move but is not registed on the server!", clientID)});

            return;
        }

        Log.d(tag, String.format("Performing move request for client: %s.", clientID));


        Player requestPlayer = Game.getPlayers().get(clientID);
        int currentPosition = requestPlayer.getPosition().getLocation();

        int steps = useDice(1, 6);
        int destination = (currentPosition + steps) % maxFields;
        Log.d(tag, String.format("Moving %s by %s steps on the map! old location was %s => new location is: %s", clientID, steps, currentPosition, destination));

        requestPlayer.moveTo(destination);

        String[] args = new String[2];
        args[0] = String.valueOf(clientID);
        args[1] = String.valueOf(destination);

        Log.d(tag, "Sending data to clients of the game!");
        server.broadcast(MainActivityType, PREFIX_PLAYER_MOVE, args);
    }

    /**
     * This method will return a number in a given range.
     * @param min The minimum value that is to be returned.
     * @param max The value that should be exceeded.
     * @return Returns a random value in between the given range.
     */
    public static int useDice(int min, int max){
        return (int) (Math.random() * max + min);
    }

}
