package ClientUIHandling.actions.cheating;

import ClientUIHandling.ClientActionInterface;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import delta.dkt.activities.GameViewActivity;

import java.util.HashMap;

import static ClientUIHandling.Constants.LOG_Cheat;

public class ActionOpenCheatMenu implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String prefix = clientMessage.split(" ")[0];
        clientMessage = clientMessage.substring(prefix.length() + 1);
        String[] args = clientMessage.split(";");

        Log.v(LOG_Cheat, "ActionOpenCheatMenu: initate client-side-process");

        int clientID;
        try {
            clientID = Integer.parseInt(args[0]);
        } catch (Exception e) {
            Log.v(LOG_Cheat, "ActionOpenCheatMenu: Aborting request => Invalid clientID received!");
            return;
        }

        if (clientID != GameViewActivity.clientID) {
            Log.v(LOG_Cheat, "ActionOpenCheatMenu: Terminate action => Player did not request cheat-menu!");
            return;
        }

        HashMap<Integer, String> playerInfos = new HashMap<>();
        for (int i = 1; i < args.length; i++) {
            String[] strings = args[i].split("#!#");
            playerInfos.put(Integer.parseInt(strings[0]), strings[1]);
        }

        Log.v(LOG_Cheat, String.format("ActionOpenCheatMenu: Attempting to open cheat-menu with %d players", playerInfos.size()));

        GameViewActivity gameViewActivity = (GameViewActivity) activity;
//        gameViewActivity.createSelectionPopup();
    }
}
