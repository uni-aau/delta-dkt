package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestCashTaskHandling implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = (String[]) parameters;

        if(args.length != 4) {
            Log.e("[SERVER] TaskHandling", "Error handling cash task -> Invalid args length args = " + args.length);
        }

        int clientID = Integer.parseInt(args[0]);
        Player player = Game.getPlayers().get(clientID);
        String taskDescriptionString = args[1];
        int taskID = Integer.parseInt(args[2]);
        int amount = Integer.parseInt(args[3]);
        String nickName = player.getNickname();

        int oldCashValue = player.getCash();
        int newCashValue = oldCashValue + amount;
        player.setCash(newCashValue);

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ACTIVITY_BROADCAST, new String[]{});
//        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_RISK_TASK, new String[]{String.valueOf(clientID), taskDescriptionString, nickName, String.valueOf(amount)});
        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_SET_MONEY, new String[]{String.valueOf(clientID), String.valueOf(newCashValue)});
    }
}
