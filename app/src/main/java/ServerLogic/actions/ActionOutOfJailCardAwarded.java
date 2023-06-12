package ServerLogic.actions;


import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_HAS_PRISONCARD;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class ActionOutOfJailCardAwarded implements ServerActionInterface {


    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        //server.broadcast(GAMEVIEW_ACTIVITY_TYPE,PREFI);
    }
}
