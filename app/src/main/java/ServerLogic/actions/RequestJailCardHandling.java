package ServerLogic.actions;

import static ClientUIHandling.Constants.GAMEVIEW_ACTIVITY_TYPE;
import static ClientUIHandling.Constants.PREFIX_ACTIVITY_BROADCAST;
import static ClientUIHandling.Constants.PREFIX_GO_TO_PRISON_FIELD;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

public class RequestJailCardHandling implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String[] args = (String[]) parameters;

        int clientId = Integer.parseInt(args[0]);
        String jailText = args[1];
        Player player = Game.getPlayers().get(clientId);
        String nickName = player.getNickname();

        // Sends player to prison
        ServerActionHandler.triggerAction(PREFIX_GO_TO_PRISON_FIELD, clientId);
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_ACTIVITY_BROADCAST, new String[]{jailText, nickName});
    }
}
