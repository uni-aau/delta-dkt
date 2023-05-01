package ServerLogic.actions;

import static ClientUIHandling.Constants.GameViewActivityType;
import static ClientUIHandling.Constants.PREFIX_ROLL_DICE_PERM;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RollDicePerm implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(GameViewActivityType, PREFIX_ROLL_DICE_PERM, new String[]{(String) parameters});
    }
}
