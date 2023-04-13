package delta.dkt.logic.structure;

import java.util.ArrayList;

public class GameMap {

    public Field getField(int location) {
        System.out.println("I am using the location parameter, which will be used when this method is implemented, to get rid of the code smell." + location);
        return null;
    }

    /**
     * This method returns the all the fields on the game-map.
     */
    public ArrayList<Field> getFields() {
        ArrayList<Field> tmpFields = new ArrayList<>();
        for (int i = 0; i < 40; i++) tmpFields.add(null);

        return tmpFields;
    }
}
