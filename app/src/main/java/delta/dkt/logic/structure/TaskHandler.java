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

        TaskCollection r = new TaskCollection();

        switch (position){
            case 3: return new RiskTaskField(3,r);
            case 9: return new RiskTaskField(9,r);
            case 23: return new RiskTaskField(23,r);
            case 28: return new RiskTaskField(28,r);
            case 38: return new RiskTaskField(38,r);
            default: return null;
        }
    }
}
