package delta.dkt.logic.structure.SpecialFields;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.SpecialField;

public class PrisonSpecialField extends SpecialField {

    private final static SpecialFieldType type = SpecialFieldType.PRISON;

    public PrisonSpecialField(int location) {
        super(location, PrisonSpecialField.type.name());
    }

    //@Override
    public void execute(Player p) {

    }

}
