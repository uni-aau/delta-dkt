package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_HOST_NEW_GAME;
import static ClientUIHandling.Constants.PREFIX_PROPLIST_UPDATE;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ClientUIHandling.ClientActionInterface;
import delta.dkt.activities.PropertyListActivity;
import delta.dkt.logic.structure.Game;
import delta.dkt.logic.structure.Property;
import delta.dkt.logic.structure.PropertyListElement;

public class ActionPropertyListUpdate implements ClientActionInterface {
    @Override
    public void execute(AppCompatActivity activity, String clientMessage) {
        String[] args = clientMessage.replace(PREFIX_PROPLIST_UPDATE, "").trim().split(";");
        ArrayList<PropertyListElement> messages = new ArrayList<>();

        System.out.println("Test = " + args[0]);
        for(int i = 0; i < args.length; i++) {
            String[] subSegments = args[i].split("#");
            int propNumber = Integer.parseInt(subSegments[0]);
            Property property = (Property) Game.getMap().getField(propNumber);
            PropertyListElement element = new PropertyListElement(subSegments[0], property.getName(), subSegments[1], "10", subSegments[2], 4);
            messages.add(element);
        }

        PropertyListActivity.messages = messages;

    }
}
