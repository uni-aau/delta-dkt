package delta.dkt.logic.structure.ActionCards;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.logic.structure.Player;

public class CashBankTask extends BankTask {
    private final int cashBankTaskAmount;

    /**
     * Creates a new risk task.
     *
     * @param id The id of the risk task
     * @param name The name of the risk task
     * @param descriptionString Tells you what task you have to do
     * @param cashAmount Tells you about the cash / steps amount
     */
    public CashBankTask(int id, String name, String descriptionString, int cashAmount) {
        super(id, name, descriptionString);
        this.cashBankTaskAmount = cashAmount;
    }

    @Override
    public void execute(Player assignee) {
        ServerActionHandler.triggerAction(Constants.PREFIX_CASH_TASK, new String[]{String.valueOf(assignee.getId()), getDescriptionString(), String.valueOf(cashBankTaskAmount)});
    }
}
