package ServerLogic;

import androidx.appcompat.app.AppCompatActivity;

import network2.ServerNetworkClient;

public interface ServerActionInterface {

    public void execute(ServerNetworkClient server, Object parameters);
}
