package ServerLogic.actions.actionCards;

import static ClientUIHandling.Constants.PREFIX_ACTIONCARD_MONEY;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class ActionMoneyActionCard implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        try {
            Object[] values = (Object[]) parameters;


            int playerId = (int)values[0];
            int amount = (int)values[1];

            String[] args = new String[2];
            args[0] = String.valueOf(Game.getClientIdByPlayerId(playerId));
            args[1] = String.valueOf(amount);

            server.broadcast(PREFIX_ACTIONCARD_MONEY+" "+args[0]+" "+args[1]);

        }catch(Exception ex){
            Log.d("Error" , ex.getMessage());
            ex.printStackTrace();
        }

    }
}
