package ServerLogic.actions.cheating;

import ServerLogic.ServerActionInterface;
import ServerLogic.handlers.ParameterHandler;
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
        String[] args = parameters.toString().split(";");
        String prefix = parameters.toString().split(" ")[0];
        if(!prefix.contains(";")){ //? Handle the case in which no prefix is provided
            if(args.length == 1){
                //? there is no prefix but also only one argument.
                args = new String[]{prefix};
            }else{
                //? there are arguments in the message, but no prefix
                parameters = parameters.toString().substring(prefix.length() + 1);
                args = parameters.toString().split(";");
            }
        }


        Log.v(LOG_CHEAT, "RequestCheatMenu: initate server-side-process");
        if (args.length == 0) {
            Log.w(LOG_CHEAT, "RequestCheatMenu: Aborting request => No arguments provided!");
            return;
        }
        
        int clientID;
        if(!ParameterHandler.hasValue(args, 0, Integer.class)) {
            Log.w(LOG_CHEAT, "RequestCheatMenu: Aborting request => Invalid clientID provided!");
            return;
        }else {
            clientID = ParameterHandler.getValue(args, 0, Integer.class);
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

        Log.v(LOG_CHEAT, String.format("RequestCheatMenu: Sending playerNames (size=%s) to client with id: %d", (sendArgs.size() - 1), clientID));
        server.broadcast(GAMEVIEW_ACTIVITY_TYPE, PREFIX_PLAYER_CHEAT_MENU, sendArgs.toArray(new String[0]));
    }
}
