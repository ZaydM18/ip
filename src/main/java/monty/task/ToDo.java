package monty.task;

import java.util.Comparator;


/**
 * Represents a ToDo task. A ToDo task contains only a description
 * and does not have a specific date or time associated with it.
 */
public class ToDo extends Task {

    /**
     * Constructs a new ToDo task with the given description.
     *
     * @param description The description of the ToDo task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Comparator for sorting {@code ToDo} tasks alphabetically by description.
     */
    public static final Comparator<ToDo> comparator = Comparator.comparing(ToDo::getDescription);

    /**
     * Returns a formatted string representation of the ToDo task
     * to be stored in a file.
     *
     * @return The string representation of the task in file format.
     */
    @Override
    public String toFileString() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Returns a string representation of the ToDo task.
     *
     * @return A string containing the task type indicator and status.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
