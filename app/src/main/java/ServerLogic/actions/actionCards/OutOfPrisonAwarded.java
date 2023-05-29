package ServerLogic.actions.actionCards;

import static ClientUIHandling.Constants.PREFIX_ACTIONCARD_OUT_OF_PRISON;


import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class OutOfPrisonAwarded implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
            int playerId = Integer.parseInt(parameters.toString());
            //Player imprisonedPlayer = Game.getPlayerById(playerId);
            int clientId = Game.getClientIdByPlayerId(playerId);

            server.broadcast(PREFIX_ACTIONCARD_OUT_OF_PRISON +" "+clientId);
    }
}
