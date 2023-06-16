package ServerLogic.actions;

import static ClientUIHandling.Constants.PREFIX_PLAYER_LOST;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestCashTaskHandling implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = (String[]) parameters;

        if (args.length != 3) {
            Log.e("[SERVER] TaskHandling", "Error handling cash task -> Invalid args length args = " + args.length);
        }

        int clientID = Integer.parseInt(args[0]);
        Player player = Game.getPlayers().get(clientID);
        String taskDescriptionString = args[1];
        int amount = Integer.parseInt(args[2]);
        String nickName = player.getNickname();

        // Sets new cash money from ris task
        int oldCashValue = player.getCash();
        int newCashValue = oldCashValue + amount;

        if (newCashValue < 0) {
            Log.i("[SERVER] TaskHandling", "New cash is smaller than 0! (OldCash/Newcash) = " + oldCashValue + " / " + newCashValue);
            ServerActionHandler.triggerAction(PREFIX_PLAYER_LOST, new String[]{String.valueOf(player.getId()), "false", "player_lost_cash_task_activity_text"}); // false -> loose event
        } else {
            player.setCash(newCashValue);
            int positiveCashAmountForBroadcast = Math.abs(amount);

            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{taskDescriptionString, nickName, String.valueOf(positiveCashAmountForBroadcast)});
            server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientID), String.valueOf(newCashValue)});
        }
    }
}
