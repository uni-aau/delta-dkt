package delta.dkt.logic.structure;

/**
 * This class represents a risk task field.
 */
public class RiskTaskField extends Field{
    /**
     * Creates a new risk task.
     * @param location location of the risk task field
     */
    public RiskTaskField(int location) {
        super(location);
    }

    public RiskTask getRiskTask(){
        return (RiskTask) TaskHandler.getTask(this.getLocation());
    }
}
