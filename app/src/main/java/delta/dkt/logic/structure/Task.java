package delta.dkt.logic.structure;

/**
 * This class represents a task.
 */
public abstract class Task {
    public int id;

    public String name;
    public String descriptionString;

    /**
     * Creates a new task.
     * @param id The id of the task
     * @param name The name of the task
     */
    public Task(int id, String name){
        this.id = id;
        this.name = name;
        this.descriptionString = descriptionString;
    }

    public abstract void execute();

}
