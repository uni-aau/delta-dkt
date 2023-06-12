package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameMap {
    private final ArrayList<Field> fields = FieldHandler.getFields();

    /**
     * This method returns a field based on the requested location.
     *
     * @param location The location of a field on the map, ranging from 1 to 40.
     * @return Returns a field, when a valid location is provided. Otherwise, null will be returned.
     */
    public Field getField(int location) {
        Optional<Field> field = fields.stream().filter(f -> f.getLocation() == location).findFirst();
        return field.orElse(null);
    }

    public Field getPrisonField(){
        return this.fields.stream().filter(f -> f instanceof PrisonField).collect(Collectors.toList()).stream().findFirst().orElseGet(null);
    }

    public Field getFieldByName(String name){
        return this.fields.stream().filter(f -> f.getName().contains(name)).findFirst().orElseGet(null);
    }

    /**
     * This method returns all the fields on the map. Note that, fields are initialized once and then stored in a local variable inside the GameMap, to preserve the field-states, such as owners, houses and more.
     *
     * @return Returns the fields of the map.
     */
    public ArrayList<Field> getFields() {
        return fields;
    }
}
