package delta.dkt.logic.structure;

/**
 * Contains the base information for a field of the game-map.
 */
public abstract class Field {
    private int location;
    private String name;


    public Field(int location) {
        this.location = location;
    }

    public int getLocation() {
        return location;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
