package delta.dkt.logic.structure;

/**
 * This class represents a risk task.
 */
public class RiskTask extends Task{

    public String riskTaskString;

    /**
     * Creates a new risk task.
     * @param id The id of the risk task
     * @param name The name of the risk task
     * @param riskTask Tells you what task you have to do
     */
    public RiskTask(int id, String name, String riskTask) {
        super(id, name);
        this.riskTaskString = riskTask;
    }
}
