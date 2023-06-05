package delta.dkt.logic.structure.SpecialFields;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.SpecialField;

public class StartSpecialField extends SpecialField {

    private final static SpecialFieldType type = SpecialFieldType.GOTOPRISON;

    public StartSpecialField(int location) {
        super(location, StartSpecialField.type.name());
    }

    //@Override
    public void execute(Player p) {
        p.suspendPlayerForRounds(3);

    }

}
