package delta.dkt.logic.structure;

public class RiskTaskField extends Field{

    RiskTask riskTask;

    public RiskTaskField(int location, RiskTask riskTask) {
        super(location);
        this.riskTask = riskTask;
    }
}
