package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_GAME_START_STATS;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class GameStartStatsRequest implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_GAME_START_STATS, new String[]{(String) parameters});
    }
}
