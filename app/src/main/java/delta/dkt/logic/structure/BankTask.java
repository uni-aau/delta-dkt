package delta.dkt.logic.structure;

/**
 * This class represents a bank task
 */

public class BankTask extends Task {

    public String bankTaskString;

    /**
     * Creates a new bank task.
     *
     * @param id       The id of the bank task
     * @param name     The name of the bank task
     * @param bankTask Tells you what task you have to do
     */

    public BankTask(int id, String name, String bankTask) {
        super(id, name);
        this.bankTaskString = bankTask;
    }
}
