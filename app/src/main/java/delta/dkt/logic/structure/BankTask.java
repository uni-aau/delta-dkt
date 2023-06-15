package delta.dkt.logic.structure;

/**
 * This class represents a bank task
 */

public class BankTask extends Task{


    /**
     * Creates a new bank task.
     * @param id The id of the bank task
     * @param name The name of the bank task
     * @param descriptionString Tells you what task you have to do
     */

    public BankTask(int id, String name, String descriptionString) {
        super(id, name, descriptionString);
    }

    @Override
    public void execute(Player assignee) {
        // Will be called in Subclasses
    }
}
