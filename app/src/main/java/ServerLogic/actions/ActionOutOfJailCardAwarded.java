package ServerLogic.actions;


import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ACTIVITY_BROADCAST;
import static ClientUIHandling.Constants.PREFIX_PRISONCARD_AWARDED_NOTIFICATION;

import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class ActionOutOfJailCardAwarded implements ServerActionInterface {


    /**
     *
     * @param server
     * @param parameters encapsulates the playerId
     */
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        //clientId is passed to serverAction
        int playerId = (int) parameters;
        String playerName = Game.getPlayers().get(playerId).getNickname();

        String[] args = new String[1];
        args[0] = playerName;

        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{"player_out_of_jail_card_activity_text", playerName});
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE,PREFIX_PRISONCARD_AWARDED_NOTIFICATION,args);
    }
}
