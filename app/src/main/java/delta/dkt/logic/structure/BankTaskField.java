package delta.dkt.logic.structure;

/**
 * This class represents a bank task field
 */
public class BankTaskField extends Field{
    /**
     * Creates a new bank task.
     * @param location location of the bank task field
     */
    public BankTaskField(int location) {
        super(location);
    }

    public BankTask getBankTask() {
        return (BankTask) TaskHandler.getTask(this.getLocation());
    }
}
