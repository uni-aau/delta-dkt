package delta.dkt.logic.structure.SpecialFields;

public enum SpecialFieldType {
    START ("Start"),
    GOTOPRISON ("Gesetztes Verletzung"),
    WEALTH_LEVY ("VermögensAbgabe"),
    PRISON ("Gefängnis"),
    TAX_LEVY ("Steuerabgabe");


    private final String name;

    private SpecialFieldType(String s) {
        name = s;
    }
}
