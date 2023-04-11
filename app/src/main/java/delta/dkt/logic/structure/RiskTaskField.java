package delta.dkt.logic.structure;

/**
 * This class represents a risk task field.
 */
public class RiskTaskField extends Field{

    RiskTask riskTask;

    /**
     * Creates a new risk task.
     * @param location location of the risk task field
     * @param riskTask the risk task that the player has to do
     */
    public RiskTaskField(int location, RiskTask riskTask) {
        super(location);
        this.riskTask = riskTask;
    }
}
