package delta.dkt.logic.structure;

/**
 * This class represents a task.
 */
public class Task {
    public int id;
    public String name;

    /**
     * Creates a new task.
     * @param id The id of the task
     * @param name The name of the task
     */
    public Task(int id, String name){
        this.id = id;
        this.name = name;
    }
}
