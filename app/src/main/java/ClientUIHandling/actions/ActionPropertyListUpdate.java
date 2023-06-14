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

        updatePropertyElements(args);
    }

    /**
     * This method extracts all properties and adds them to the property view list
     */
    private void updatePropertyElements(String[] args){
        ArrayList<PropertyListElement> propertyElements = new ArrayList<>();
        for (String arg : args) {
            String[] propertyValues = arg.split("#"); // Splits String into all variables

            String propertyNumber = propertyValues[0];
            String propertyPrice = propertyValues[1];
            String propertyBaseRent = propertyValues[2];
            String propertyOwner = propertyValues[3];
            int propertyHouses = Integer.parseInt(propertyValues[4]);
            Property property = (Property) Game.getMap().getField(Integer.parseInt(propertyNumber));

            PropertyListElement element = new PropertyListElement(propertyNumber, property.getName(), propertyPrice, propertyBaseRent, propertyOwner, propertyHouses);
            propertyElements.add(element);
        }
        PropertyListActivity.propertyElements = propertyElements;
    }
}
