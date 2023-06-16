package ServerLogic.actions;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RequestAskBuyProperty implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = (String[]) parameters;

        int clientID = Integer.parseInt(args[0]);
        int position = Integer.parseInt(args[1]);

        Log.d("[Server] RequestAskBuyProperty", "Received property popup request! ClientID = " + clientID);

        server.broadcast(Constants.GAMEVIEW_ACTIVITY_TYPE, Constants.PREFIX_ASK_BUY_PROPERTY, new String[]{String.valueOf(clientID), String.valueOf(position)});
    }
}
