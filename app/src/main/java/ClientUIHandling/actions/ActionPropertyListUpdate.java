package ClientUIHandling.actions;

import static ClientUIHandling.Constants.PREFIX_PROPLIST_UPDATE;

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
        ArrayList<PropertyListElement> propertyElements = new ArrayList<>();
//        args.add(String.format("%d#%s#%d#%d#%d", i, owner, property.getPrice(), property.getBaseRent(), property.getHouses()));

        for (String arg : args) {
            String[] subSegments = arg.split("#");
            int propNumber = Integer.parseInt(subSegments[0]);
            Property property = (Property) Game.getMap().getField(propNumber);

            String propertyNumber = subSegments[0];
            String propertyPrice = subSegments[1];
            String propertyBaseRent = subSegments[2];
            String propertyOwner = subSegments[3];
            int propertyHouses = Integer.parseInt(subSegments[4]);

            PropertyListElement element = new PropertyListElement(propertyNumber, property.getName(), propertyPrice, propertyBaseRent, propertyOwner, propertyHouses);
            propertyElements.add(element);
        }
        PropertyListActivity.messages = propertyElements;
    }
}
