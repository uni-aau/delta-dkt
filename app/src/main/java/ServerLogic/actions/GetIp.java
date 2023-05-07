package ServerLogic.actions;


import ClientUIHandling.Constants;
import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class GetIp implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        server.broadcast(Constants.LOBBYVIEW_ACTIVITY_TYPE, Constants.PREFIX_GET_IP, new String[]{server.getIP()});
    }
}
