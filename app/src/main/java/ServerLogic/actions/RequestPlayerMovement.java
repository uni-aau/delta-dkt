package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_PLAYER_MOVE;
import static ClientUIHandling.Constants.PREFIX_START_CASH_VALUE;
import static ClientUIHandling.Constants.PREFIX_SUSPENSION_COUNT;

import ClientUIHandling.Config;
import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

import java.util.ArrayList;

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


        if(requestPlayer.isSuspended()){
            requestPlayer.reduceSuspension();
            Log.d(tag, String.format("Aborting move request for client: (%s), because he is in prison", clientID));
            String[] notifyArgs = new String[1];
            //args[0] = String.valueOf(requestPlayer.getId());
            notifyArgs[0] = String.valueOf(requestPlayer.getSuspention());
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_SUSPENSION_COUNT, notifyArgs);
            return;
        }


        requestPlayer.setCheat(isCheating);
        int currentPosition = requestPlayer.getPosition().getLocation();

        int steps = Config.DICE_RANGE.getRandomValue();
        if(isCheating) steps = Config.CHEAT_RANGE.getRandomValue(); //? super-dice

        int destination = (currentPosition + steps) % maxFields;
        if (destination == 0) destination++;
        //destination = 11;
        //* detailed logs
        Log.d(tag, String.format("Moving Player%s to position=%s, dice value=%s!", clientID, destination, steps));



        ArrayList<String> sendArgs = new ArrayList<>();
        sendArgs.add(String.valueOf(clientID));
        sendArgs.add(String.valueOf(destination));
        sendArgs.add(String.valueOf(steps));
        sendArgs.add(requestPlayer.getNickname());

        Log.d(tag, String.format("Sending out messages to %s players.", Game.getPlayers().size()));
        Log.d(tag, "");

        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, sendArgs.toArray(new String[0]));
        requestPlayer.moveTo(destination); //Moved after Move-Broadcast to move the player to the PrisonField after he landed on the GoToPrisonField

        //? Add start-cash for passing over the start field.
        if (currentPosition > destination) ServerActionHandler.triggerAction(PREFIX_START_CASH_VALUE, clientID);
    }
}
