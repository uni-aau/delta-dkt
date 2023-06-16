package delta.dkt.logic.structure;

import java.util.ArrayList;

public class FieldHandler {

    private FieldHandler() {
    }

    public static ArrayList<Field> getFields() {
        ArrayList<Field> fields = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            if (PropertyHandler.getProperties(i) != null) fields.add(PropertyHandler.getProperties(i));

            if(i == 3 || i == 23 || i == 38) fields.add(new RiskTaskField(i));
            if(i == 9 || i == 28) fields.add(new BankTaskField(i));

            if (i == 1) fields.add(new SpecialField(i, "Start"));
            if (i == 11) fields.add(new GoToPrisonField(i, "Gesetzes Verletzung"));
            if (i == 21) fields.add(new SpecialField(i, "VermögensAbgabe"));
            if (i == 31) fields.add(new PrisonField(i, "Gefängnis"));
            if (i == 33) fields.add(new SpecialField(i, "Steuerabgabe"));
        }

        return fields;
    }

}
