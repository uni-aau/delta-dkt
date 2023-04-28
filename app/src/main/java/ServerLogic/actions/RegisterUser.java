package ServerLogic.actions;

import android.util.Log;

import ServerLogic.ServerActionInterface;
import network2.ServerNetworkClient;

public class RegisterUser implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        Log.d("Server Register User", "Register User request received! Server: " + server + " parameters: " + parameters);

        // TODO
    }
}
