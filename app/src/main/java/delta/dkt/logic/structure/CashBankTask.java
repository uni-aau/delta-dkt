package delta.dkt.logic.structure;

public class CashBankTask extends BankTask {
    /**
     * Creates a new risk task.
     *
     * @param id The id of the risk task
     * @param name The name of the risk task
     * @param descriptionString Tells you what task you have to do
     * @param amount Tells you about the cash / steps amount
     */
    public CashBankTask(int id, String name, String descriptionString, int amount) {
        super(id, name, descriptionString);
    }
}
