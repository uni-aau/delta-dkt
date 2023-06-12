package delta.dkt.logic.structure.ActionCards;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Task;

public class OutOfJailCard extends Task {

    /**
     * Creates a new task.
     *
     * @param id   The id of the task
     * @param name The name of the task
     */
    public OutOfJailCard(int id, String name, String actionCardText) {
        super(id, name, actionCardText);
    }


    @Override
    public void execute(Player asignee) {
        asignee.setYouGetOutOfPrisonCard(true);

        //send message to client that he is suspended for so many rounds

        ServerActionHandler.triggerAction(Constants.PREFIX_PRISONCARD_AWARDED,asignee.getId());
    }

}