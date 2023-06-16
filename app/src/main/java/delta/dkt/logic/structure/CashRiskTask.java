package delta.dkt.logic.structure;

import android.util.Log;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;

public class CashRiskTask extends RiskTask {
    private final int cashAmount;

    /**
     * Creates a new risk task.
     *
     * @param id The id of the risk task
     * @param name The name of the risk task
     * @param descriptionString Tells you what task you have to do
     * @param cashAmount Tells you about the cash / steps amount
     */
    public CashRiskTask(int id, String name, String descriptionString, int cashAmount) {
        super(id, name, descriptionString);
        this.cashAmount = cashAmount;
    }

    @Override
    public void execute(Player assignee) {
        Log.d("RiskTask", "Sending RiskTask execution to server");

        ServerActionHandler.triggerAction(Constants.PREFIX_CASH_TASK, new String[]{String.valueOf(assignee.getId()), getDescriptionString(), String.valueOf(cashAmount)});
    }
}
