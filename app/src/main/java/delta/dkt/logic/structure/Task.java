package delta.dkt.logic.structure;

/**
 * This class represents a task.
 */
public class Task {
    private final int id;
    private final String name;

    /**
     * Creates a new task.
     * @param id The id of the task
     * @param name The name of the task
     */
    public Task(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
