package ServerLogic;

import network2.ServerNetworkClient;

public interface ServerActionInterface {

    public void execute(ServerNetworkClient server, Object parameters);
}
