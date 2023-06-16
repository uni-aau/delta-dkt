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
    public static Task getTask(int position){
        RiskTaskCollection r = new RiskTaskCollection();
        BankTaskCollection b = new BankTaskCollection();

        if(Game.getMap().getField(position) instanceof BankTaskField) return b.getRandomBankTask();
        if(Game.getMap().getField(position) instanceof RiskTaskField) return r.getRandomRiskTask();

        return null;
    }
}
