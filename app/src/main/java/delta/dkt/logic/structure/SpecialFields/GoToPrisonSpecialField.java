package delta.dkt.logic.structure.SpecialFields;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.SpecialField;

public class GoToPrisonSpecialField extends SpecialField {

    private final static SpecialFieldType type = SpecialFieldType.GOTOPRISON;

    public GoToPrisonSpecialField(int location) {
        super(location, GoToPrisonSpecialField.type.name());
    }

    //@Override
    public void execute(Player p) {
        p.suspendPlayerForRounds(3);

    }

}
