package monty.task;

/**
 * Represents a task with a description and completion status.
 * This is an abstract class that provides common behavior for different types of tasks.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;
    
    /**
     * Constructs a new Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Sets the completion status of the task.
     *
     * @param done True if the task is done, false otherwise.
     */
    public void setDone(boolean done) {
        this.isDone = done;
    }

    public String getDescription() {
        assert description != null : "Task description should never be null";
        return this.description;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if the task is done, otherwise a space character.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns a string representation of the task for file storage.
     *
     * @return A formatted string representation of the task.
     */
    public abstract String toFileString();

    /**
     * Returns a string representation of the task.
     *
     * @return A formatted string representation including status and description.
     */
    @Override
    public String toString() {
        assert description != null : "Task description should never be null";
        return "[" + getStatusIcon() + "] " + description;
    }
}
