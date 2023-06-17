package delta.dkt.logic.structure.actioncards;

import static ClientUIHandling.Constants.PREFIX_JAIL_TASK;

import ServerLogic.ServerActionHandler;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Task;

/**
 * This class represents a risk task.
 */
public class RiskTask extends Task {


    /**
     * Creates a new risk task.
     *
     * @param id The id of the risk task
     * @param name The name of the risk task
     * @param descriptionString Tells you what task you have to do
     */
    public RiskTask(int id, String name, String descriptionString) {
        super(id, name, descriptionString);
    }

    @Override
    public void execute(Player assignee) {
        // Only jail tasks execute the general bankTask method
        ServerActionHandler.triggerAction(PREFIX_JAIL_TASK, new String[]{String.valueOf(assignee.getId()), getDescriptionString()});
    }
}
