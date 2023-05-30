package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_START_CASH_VALUE;

import ClientUIHandling.Config;
import android.util.Log;

import ServerLogic.ServerActionHandler;
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
        Log.d(tag, String.format("Performing move request for Player%s: ", clientID));
        if(isCheating) Log.w(tag, "Cheatmode has been enabled for this move by the player!");


        Player requestPlayer = Game.getPlayers().get(clientID);
        requestPlayer.setCheat(isCheating);
        int currentPosition = requestPlayer.getPosition().getLocation();

        int steps = Config.diceRange.getRandomValue();
        if(isCheating) steps = Config.CheatRange.getRandomValue(); //? super-dice

        int destination = (currentPosition + steps) % maxFields;
        if (destination == 0) destination++;

        //* detailed logs
        Log.d(tag, String.format("Moving Player%s to position=%s, dice value=%s!", clientID, destination, steps));

        requestPlayer.moveTo(destination);

        String[] sendArgs = new String[3];
        sendArgs[0] = String.valueOf(clientID);
        sendArgs[1] = String.valueOf(destination);
        sendArgs[2] = String.valueOf(steps);

        Log.d(tag, String.format("Sending out messages to %s players.", Game.getPlayers().size()));
        Log.d(tag, "");
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, sendArgs);

        //? Add start-cash for passing over the start field.
        if (currentPosition > destination) ServerActionHandler.triggerAction(PREFIX_START_CASH_VALUE, clientID);
    }
}
