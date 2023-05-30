package ServerLogic.actions.cheating;

import ServerLogic.ServerActionHandler;
import ServerLogic.ServerActionInterface;
import ServerLogic.handlers.ParameterHandler;
import android.annotation.SuppressLint;
import android.util.Log;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Player;
import network2.ServerNetworkClient;

import static ClientUIHandling.Constants.*;

@SuppressWarnings("DataFlowIssue")
public class RequestReportCheater implements ServerActionInterface {

    @SuppressLint("DefaultLocale")
    @Override
    public void execute(ServerNetworkClient server, Object parameters) {
        String actionPrefix = parameters.toString().split(" ")[0];
        parameters = parameters.toString().substring(actionPrefix.length() + 1);
        String[] actionArgs = parameters.toString().split(";");

        Log.v(LOG_CHEAT, "RequestReportCheater: initate server-side-process");
        if (actionArgs.length == 0) {
            Log.w(LOG_CHEAT, "RequestReportCheater: Aborting request => No arguments provided!");
            return;
        }

        if(!ParameterHandler.hasValue(actionArgs, 0, Integer.class)) {
            Log.w(LOG_CHEAT, "RequestReportCheater: Aborting request => Invalid clientID provided!");
            return;
        }
        if(!ParameterHandler.hasValue(actionArgs, 1, Integer.class)){
            Log.w(LOG_CHEAT, "RequestReportCheater: Aborting request => Invalid accused-clientID provided!");
            return;
        }

        int clientID = ParameterHandler.getValue(actionArgs, 0, Integer.class);
        Integer accusedClientID = ParameterHandler.getValue(actionArgs, 1, Integer.class);


        Player accusedCheater = Game.getPlayers().get(accusedClientID);

        if(accusedCheater.hasCheated()){
            Log.d(LOG_CHEAT, String.format("RequestReportCheater: Player%d has cheated and will be punished for it!", accusedClientID));

            //? reported player has actually cheated and will get punished for it
            ServerActionHandler.triggerAction(PREFIX_PLAYER_CHEATED, new Object[]{true, accusedClientID});
        }else{
            Log.d(LOG_CHEAT, String.format("RequestReportCheater: Player%d has falsely reported a player and will get punished for it!", clientID));

            //? user has falsely reported a player and will get punished for it
            ServerActionHandler.triggerAction(PREFIX_PLAYER_CHEATED, new Object[]{false, clientID});
        }

        Log.v(LOG_CHEAT, "RequestReportCheater: Server-side-process finished!");
    }
}
