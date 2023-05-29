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
        asignee.addGetOutOfJailCard();

        //send message to client that he is suspended for so many rounds
        ServerActionHandler.triggerAction(Constants.PREFIX_ACTIONCARD_OUT_OF_PRISON,asignee.getId());
    }

}
