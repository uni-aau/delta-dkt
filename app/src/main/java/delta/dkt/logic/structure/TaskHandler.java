package delta.dkt.logic.structure;

/**
 * This class represents a task handler to handle all the tasks
 */
public class TaskHandler {

    private TaskHandler() {
        // no instantiation of class
    }

    /**
     * Gets a random task out of the BankTaskCollection/RiskTaskCollection of a given position
     * @param position The position of the property.
     */
    public static Field getTaskFieldForPosition(int position){

        RiskTaskCollection r = new RiskTaskCollection();

        switch (position){
            case 3: return new RiskTaskField(3,r.getRandomRiskTask());
            case 9: return new RiskTaskField(9,r.getRandomRiskTask());
            case 23: return new RiskTaskField(23,r.getRandomRiskTask());
            case 28: return new RiskTaskField(28,r.getRandomRiskTask());
            case 38: return new RiskTaskField(38,r.getRandomRiskTask());
            default: return null;
        }
    }
}
