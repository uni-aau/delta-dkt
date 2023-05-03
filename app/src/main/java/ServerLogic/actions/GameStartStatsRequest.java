package ServerLogic.actions;

import static ClientUIHandling.Constants.GameViewActivityType;
import static ClientUIHandling.Constants.PREFIX_GAME_START_STATS;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class GameStartStatsRequest implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(GameViewActivityType, PREFIX_GAME_START_STATS, new String[]{(String) parameters});
    }
}
