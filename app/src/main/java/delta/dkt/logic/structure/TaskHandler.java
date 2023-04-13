package delta.dkt.logic.structure;

/**
 * This class represents a task handler to handle all the tasks
 */
public class TaskHandler {

    /**
     * Gets a random task out of the BankTaskCollection/RiskTaskCollection of a given position
     *
     * @param position The position of the property.
     */
    public static Field getTask(int position) {

        RiskTaskCollection r = new RiskTaskCollection();
        BankTaskCollection b = new BankTaskCollection();

        switch (position) {
            case 3:
                return new RiskTaskField(3, r.getRandomRiskTask());
            case 9:
                return new BankTaskField(9, b.getRandomBankTask());
            case 23:
                return new RiskTaskField(23, r.getRandomRiskTask());
            case 28:
                return new BankTaskField(28, b.getRandomBankTask());
            case 33:
                return new BankTaskField(33, b.getRandomBankTask());
            case 38:
                return new RiskTaskField(38, r.getRandomRiskTask());
            default:
                return null;
        }
    }
}
