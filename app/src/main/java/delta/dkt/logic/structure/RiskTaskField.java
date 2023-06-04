package delta.dkt.logic.structure;

/**
 * This class represents a risk task field.
 */
public class RiskTaskField extends Field{

    TaskCollection taskStapel;

    /**
     * Creates a new risk task.
     * @param location location of the risk task field
     * @param taskStapel the risk task that the player has to do
     */
    public RiskTaskField(int location, TaskCollection taskStapel) {
        super(location);
        this.taskStapel = taskStapel;
    }

    public Task drawCard(){
        return taskStapel.getRandomTask();
    }
}
