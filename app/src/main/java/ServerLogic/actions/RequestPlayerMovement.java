package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

@SuppressWarnings("FieldCanBeLocal")
public class RequestPlayerMovement implements ServerActionInterface {
    private String tag = "Movement-" + getClass().getSimpleName();
    private int maxFields = 41;


    @SuppressWarnings("DataFlowIssue")
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String prefix = parameters.toString().split(" ")[0];
        parameters = parameters.toString().substring(prefix.length()+1);

        String[] args = parameters.toString().split(";");

        int clientID;
        //? Checking whether the id of the request-player is valid.
        try {
            clientID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            Log.e("Movement", String.format("Parsing the clientsID failed, as its format is invalid! (%s)", parameters));

            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, new String[]{"error", "Someone tried to move but provided an invalid id."});
            return;
        }

        //? Player is not inside the players 'collection' of the game, thus cannot be worked with.
        if (!Game.getPlayers().containsKey(clientID)) {
            Log.e(tag, String.format("A player with the id: %s requested to move, but is not yet registered on the server side. Aborting request!", clientID));
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, new String[]{"error", String.format("Player %s tried to move but is not registed on the server!", clientID)});

            return;
        }

        boolean isCheating = args.length >= 2 && args[1].equalsIgnoreCase("true");

        Log.d(tag, "");
        Log.d(tag, String.format("Performing move request for client: (%s).", clientID));
        if(isCheating) Log.w(tag, "Cheatmode has been enabled for this move by the player!");


        Player requestPlayer = Game.getPlayers().get(clientID);
        int currentPosition = requestPlayer.getPosition().getLocation();

        int steps = useDice(1, 6);
        int destination = (currentPosition + steps) % maxFields;
        if (destination == 0) destination++;

        //* detailed logs
        Log.d(tag, String.format("Moving player (id=%s) to (pos=%s)!", clientID, destination));
        Log.d(tag + "-detail", String.format("\told position: (pos=%s), new position: (pos=%s), steps: (steps=%s)", currentPosition, destination, steps));

        requestPlayer.moveTo(destination);

        String[] sendArgs = new String[2];
        sendArgs[0] = String.valueOf(clientID);
        sendArgs[1] = String.valueOf(destination);

        Log.d(tag, String.format("Sending out messages to %s players.", Game.getPlayers().size()));
        Log.d(tag, "");
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, sendArgs);
    }

    /**
     * This method will return a number in a given range.
     *
     * @param min The minimum value that is to be returned.
     * @param max The value that should be exceeded.
     * @return Returns a random value in between the given range.
     */
    public static int useDice(int min, int max) {
        //START-NOSCAN
        return (int) (Math.random() * max + min);
        //END-NOSCAN
    }

}
