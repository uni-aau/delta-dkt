package delta.dkt.logic.structure;

import java.util.ArrayList;

public class RiskTaskCollection {
    private ArrayList<RiskTask> riskTask = new ArrayList<>();

    public RiskTaskCollection(){
        riskTask.add(new RiskTask(1,"RiskCard1","Do something"));
        riskTask.add(new RiskTask(2,"RiskCard2","Do another thing"));
    }
}
