package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Optional;

public class GameMap {
    private ArrayList<Field> fields = FieldHandler.getFields();

    public Field getField (int location) {
        System.out.println("I am using the location parameter, which will be used when this method is implemented, to get rid of the code smell." + location);
        Property testProperty = new Property(0,10,1,PropertyLevel.CHEAP,10);
        testProperty.setOwner(Player.testInstance);
        return testProperty;
    }

    /**
     * This method returns all the fields on the map. Note that, fields are initialized once and then stored in a local variable inside the GameMap, to preserve the field-states, such as owners, houses and more.
     * @return Returns the fields of the map.
     */
    public ArrayList<Field> getFields () {
        return fields;
    }
}
