package delta.dkt.logic.structure;

public class RiskTask extends Task{

    public String riskTask;

    public RiskTask(int id, String name, String riskTask) {
        super(id, name);
        this.riskTask = riskTask;
    }
}
