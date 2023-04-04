package delta.dkt.logic.structure;

public class RiskTask extends Task{

    public String riskTaskString;

    public RiskTask(int id, String name, String riskTask) {
        super(id, name);
        this.riskTaskString = riskTask;
    }
}
