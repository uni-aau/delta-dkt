package delta.dkt.logic.structure.ActionCards;

import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;
import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Task;

public class PlayerPositionCard extends Task {

    private final boolean isRelative;
    private final int destination;

    /**
     *
     * @param id: the id of the actionCard (task)
     * @param name: the name of the actionCard (task)
     * @param actionCardText: the text describing the action of the actionCard (task)
     * @param destination: if isRelative, destination contains the amount of fields to move (positive is forward, negative is backward).
     *                    if  isRelative=false, then destination contains the absolute number of the destination field.
     * @param isRelative: decides how to interpret the destination parameter
     */
    public PlayerPositionCard(int id, String name, String actionCardText, int destination, boolean isRelative) {
        super(id, name, actionCardText);
        this.destination = destination;
        this.isRelative = isRelative;
    }


    @Override
    public void execute(Player asignee) {
        //determine Player Position
        int currentPos = asignee.getPosition().getLocation();
        if(this.isRelative){
            asignee.moveSteps(this.destination);
        }else{
            asignee.moveTo(this.destination);
        }
        int[] args ={ asignee.getId(), asignee.getPosition().getLocation()};
        ServerActionHandler.triggerAction(Constants.PREFIX_ACTIONCARD_TRAVEL,args);
    }

}
