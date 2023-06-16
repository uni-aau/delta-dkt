package delta.dkt.logic.structure;

/**
 * This class represents a bank task field
 */
public class BankTaskField extends Field{

    private final BankTask bankTask;

    /**
     * Creates a new bank task.
     * @param location location of the bank task field
     * @param bankTask the bank task that the player has to do
     */
    public BankTaskField(int location, BankTask bankTask) {
        super(location);
        this.bankTask = bankTask;
    }
}
