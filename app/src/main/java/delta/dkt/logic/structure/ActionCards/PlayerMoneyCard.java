package delta.dkt.logic.structure.ActionCards;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Task;

public class PlayerMoneyCard extends Task {

    private final int amount;

    /**
     * Creates a new task.
     *
     * @param id   The id of the task
     * @param name The name of the task
     */
    public PlayerMoneyCard(int id, String name, String actionCardText, int amount) {
        super(id, name, actionCardText);
        this.amount = amount;
    }


    @Override
    public void execute(Player assignee) {

        assignee.setCash(assignee.getCash()+amount);

        if(assignee.getCash() < 0){
            ServerActionHandler.triggerAction(Constants.PREFIX_PLAYER_LOST,assignee.getId());
        }

        int[] params = { assignee.getId(), amount};

        //send message to clients that player just paid money
        ServerActionHandler.triggerAction(Constants.PREFIX_ACTIONCARD_MONEY, params );

    }

}
