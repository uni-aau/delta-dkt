package ServerLogic.actions.cheating;

import ServerLogic.ServerActionInterface;
import android.annotation.SuppressLint;
import android.util.Log;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

import java.util.ArrayList;
import java.util.Collections;

import static ClientUIHandling.Constants.*;

@SuppressWarnings("DataFlowIssue")
public class RequestCheatMenu implements ServerActionInterface {

    @SuppressLint("DefaultLocale")
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String prefix = parameters.toString().split(" ")[0];
        parameters = parameters.toString().substring(prefix.length() + 1);
        String[] args = parameters.toString().split(";");

        Log.v(LOG_Cheat, "RequestCheatMenu: initate server-side-process");
        if (args.length == 0) {
            Log.w(LOG_Cheat, "RequestCheatMenu: Aborting request => No arguments provided!");
            return;
        }

        int clientID;
        try {
            clientID = Integer.parseInt(args[0]);
        } catch (Exception e) {
            Log.w(LOG_Cheat, "RequestCheatMenu: Aborting request => Invalid clientID provided!");
            return;
        }


        ArrayList<Integer> sortedKeys = new ArrayList<>(Game.getPlayers().keySet());
        Collections.sort(sortedKeys);

        ArrayList<String> sendArgs = new ArrayList<>();
        sendArgs.add(String.valueOf(clientID));

        for (int key : sortedKeys) {
            Player p = Game.getPlayers().get(key);

            //? => 1#!#MaxMuster;2#!#MoritzMuster;3#!#MiaMuster; ....
            sendArgs.add(String.format("%d#!#%s", key, p.getNickname()));
        }

        Log.v(LOG_Cheat, String.format("RequestCheatMenu: Sending playerNames (size=%s) to client with id: %d", (sendArgs.size() - 1), clientID));
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_CHEAT_MENU, sendArgs.toArray(new String[0]));
    }
}
