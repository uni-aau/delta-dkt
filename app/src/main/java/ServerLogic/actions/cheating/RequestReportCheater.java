package ServerLogic.actions.cheating;

import ServerLogic.ServerActionHandler;
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
public class RequestReportCheater implements ServerActionInterface {

    @SuppressLint("DefaultLocale")
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String prefix = parameters.toString().split(" ")[0];
        parameters = parameters.toString().substring(prefix.length() + 1);
        String[] args = parameters.toString().split(";");

        Log.v(LOG_Cheat, "RequestReportCheater: initate server-side-process");
        if (args.length == 0) {
            Log.w(LOG_Cheat, "RequestReportCheater: Aborting request => No arguments provided!");
            return;
        }

        int clientID;
        try {
            clientID = Integer.parseInt(args[0]);
        } catch (Exception e) {
            Log.w(LOG_Cheat, "RequestReportCheater: Aborting request => Invalid clientID provided!");
            return;
        }

        int accusedClientID;
        try{
            accusedClientID = Integer.parseInt(args[1]);
        }catch (Exception e){
            Log.w(LOG_Cheat, "RequestReportCheater: Aborting request => Invalid accused-clientID provided!");
            return;
        }

        Player accusedCheater = Game.getPlayers().get(accusedClientID);

        if(accusedCheater.hasCheated()){
            Log.d(LOG_Cheat, String.format("RequestReportCheater: Player%d has cheated and will be punished for it!", accusedClientID));

            //? reported player has actually cheated and will get punished for it
            ServerActionHandler.triggerAction(PREFIX_PLAYER_CHEATED, new Object[]{true, accusedClientID});
        }else{
            Log.d(LOG_Cheat, String.format("RequestReportCheater: Player%d has falsely reported a player and will get punished for it!", clientID));

            //? user has falsely reported a player and will get punished for it
            ServerActionHandler.triggerAction(PREFIX_PLAYER_CHEATED, new Object[]{false, clientID});
        }

        Log.v(LOG_Cheat, "RequestReportCheater: Server-side-process finished!");
    }
}