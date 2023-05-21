package ServerLogic.actions;

import android.util.Log;
import ServerLogic.ServerActionInterface;
import delta.dkt.logic.structure.Game;
import network2.ServerNetworkClient;

public class ActionPrison implements ServerActionInterface {
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        {
            Log.i("INFO", "TRIGGERED ACTION");
            int id = (int) parameters;
            if (!Game.getPlayers().containsKey(id)) {
                Log.e("ERROR", "Wrong player id: " + id);
                return;
            }
        }
    }
}
