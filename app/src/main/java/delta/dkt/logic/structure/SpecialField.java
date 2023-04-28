package delta.dkt.logic.structure;

public class SpecialField extends Field {

    public SpecialField(int location) {
        super(location);
    }

    public SpecialField(int location, String name) {
        super(location);
        this.setName(name);
    }
}
