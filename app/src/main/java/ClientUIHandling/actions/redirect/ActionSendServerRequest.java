package ClientUIHandling.actions.redirect;

import ClientUIHandling.ClientActionInterface;
import ServerLogic.ServerActionHandler;
import ServerLogic.handlers.ParameterHandler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import delta.dkt.activities.MainMenuActivity;

import java.util.ArrayList;
import java.util.Arrays;

import static ClientUIHandling.Constants.LOG_MAIN;

public class ActionSendServerRequest implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.split(";"); //? Handle the case in which the message has no prefix
        String prefix = clientMessage.split(" ")[0];
        if(!prefix.contains(";")){
            //* If the message has a prefix, then the prefix will be removed, and the rest of the message will be split by ";"
            args = clientMessage.replace(prefix, "").trim().split(";");
        }

        if(!ParameterHandler.hasValue(args,0, String.class)) {
            Log.w(LOG_MAIN, "ActionSendServerRequest: Aborting request => Because there the server-action-prefix has not been provided on the first position!");
            return;
        }

        String serverActionPrefix = ParameterHandler.getValue(args,0, String.class);

        if (MainMenuActivity.role) {
            //? The client that hosts the server will redirect the request to the server.
            ArrayList<String> parameters = new ArrayList<>(Arrays.asList(args));
            parameters.remove(0); //? parameters => purely the arguments that are passed to the action (no prefix)

            //? Format the message to start with the prefix, sperated by space and then the arguments for legacy actions
            String message = serverActionPrefix + " " + String.join(" ", parameters);

            Log.d(LOG_MAIN, "ActionSendServerRequest: Triggering server-action: prefix=" + serverActionPrefix + " with message: " + message);
            ServerActionHandler.triggerAction(serverActionPrefix, message);
        }

    }
}
