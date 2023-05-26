package delta.dkt.logic.structure;

public class PropertyListElement {
    private final String propNumber;
    private final String propName;
    private final String propPrice;
    private final String propRent;
    private final String propOwner;
    private final int propAmount;

    public PropertyListElement(String propNumber, String propName, String propPrice, String propRent, String propOwner, int propAmount) {
        this.propNumber = propNumber;
        this.propName = propName;
        this.propPrice = propPrice;
        this.propRent = propRent;
        this.propOwner = propOwner;
        this.propAmount = propAmount;
    }

    public String getPropNumber() {
        return propNumber;
    }

    public String getPropName() {
        return propName;
    }

    public String getPropPrice() {
        return propPrice;
    }

    public String getPropRent() {
        return propRent;
    }

    public String getPropOwner() {
        return propOwner;
    }

    public int getPropAmount() {
        return propAmount;
    }
}
