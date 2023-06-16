package ServerLogic.actions;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import android.util.Log;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.*;
import network2.ServerNetworkClient;

import java.util.ArrayList;

import static ClientUIHandling.Constants.*;

@SuppressWarnings("FieldCanBeLocal")
public class RequestPlayerMovement implements ServerActionInterface {
    private String tag = "Movement-" + getClass().getSimpleName();
    private final int maxFields = 41;


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
            Log.d(tag, String.format("Aborting move request for client: (%s), because he is in prison", clientID));
            String[] notifyArgs = new String[2];
            notifyArgs[0] = String.valueOf(requestPlayer.getSuspention());
            notifyArgs[1] = String.valueOf(clientID);
            server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_SUSPENSION_COUNT, notifyArgs);
            requestPlayer.reduceSuspension();
            return;
        }


        requestPlayer.setCheat(isCheating);
        int currentPosition = requestPlayer.getPosition().getLocation();

        int steps = Config.DICE_RANGE.getRandomValue();
        if(isCheating) steps = Config.CHEAT_RANGE.getRandomValue(); //? super-dice

        int destination = (currentPosition + steps) % maxFields;
        if (destination == 0) destination++;

        //* detailed logs
        Log.d(tag, String.format("Moving Player%s to position=%s, dice value=%s!", clientID, destination, steps));

        ArrayList<String> sendArgs = new ArrayList<>();
        sendArgs.add(String.valueOf(clientID));
        sendArgs.add(String.valueOf(destination));
        sendArgs.add(String.valueOf(steps));
        sendArgs.add(requestPlayer.getNickname());

        Log.d(tag, String.format("Sending out messages to %s players.", Game.getPlayers().size()));
        Log.d(tag, "");

        requestPlayer.moveTo(destination);

        //* In case the player lands on the GoToPrisonField, he will be moved to the PrisonField.
        if(Game.getMap().getField(destination) instanceof GoToPrisonField){
            Log.d(tag, String.format("Player%s landed on a GoToPrisonField, thus he will be moved to the PrisonField! (Overriding Movement-Action)", clientID));
            ServerActionHandler.triggerAction(PREFIX_GO_TO_PRISON_FIELD, clientID);
            return;
        }

        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_MOVE, sendArgs.toArray(new String[0]));


        //? Add start-cash for passing over the start field.
        if (currentPosition > destination) ServerActionHandler.triggerAction(PREFIX_START_CASH_VALUE, clientID);
    }

    private void handleSpecialEvents(Player player){
        Field field = player.getPosition();

        if (field instanceof Property) {
            Property prop = (Property) field;

            //? The property is not owned by anyone, thus the player can buy it.
            if(prop.getOwner() == null) ServerActionHandler.triggerAction(PREFIX_ASK_BUY_PROPERTY, new String[]{String.valueOf(player.getId()), String.valueOf(player.getPosition().getLocation())});

            //? Pay rent to the owner of the property.
            ServerActionHandler.triggerAction(PREFIX_PLAYER_PAYRENT, player.getId());
        }

        if (field instanceof SpecialField) {
            if (field.getName().equals("VermögensAbgabe") || field.getName().equals("Steuerabgabe")) {
                ServerActionHandler.triggerAction(Constants.PREFIX_PAY_TAX, player.getId());
            }
        }

        if(field instanceof RiskTaskField || field instanceof BankTaskField){
            Task task = TaskHandler.getTask(field.getLocation());
            task.execute(player);
        }
    }

}
