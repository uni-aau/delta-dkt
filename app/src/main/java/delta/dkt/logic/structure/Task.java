package delta.dkt.logic.structure;

/**
 * This class represents a task.
 */
public abstract class Task {
    private final int id;
    private final String name;

    private final String descriptionString;

    /**
     * Creates a new task.
     *
     * @param id The id of the task
     * @param name The name of the task
     */
    protected Task(int id, String name, String descriptionString) {
        this.id = id;
        this.name = name;
        this.descriptionString = descriptionString;
    }

    public abstract void execute(Player asignee);

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDescriptionString() {
        return descriptionString;
    }
}
