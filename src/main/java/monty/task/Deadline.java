package monty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a specific due date and time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private LocalDateTime by;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the deadline task.
     * @param by          The due date and time in "yyyy-MM-dd HHmm" format.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    /**
     * Parses a date-time string into a {@code LocalDateTime} object.
     *
     * @param dateTime The date-time string in "yyyy-MM-dd HHmm" format.
     * @return The corresponding {@code LocalDateTime} object.
     * @throws IllegalArgumentException If the input string is not in the correct format.
     */
    private LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(" Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2024-05-30 1800).");
        }
    }

    /**
     * Returns the due date and time of the deadline task.
     *
     * @return The due date as a {@code LocalDateTime} object.
     */
    public LocalDateTime getDate() {
        return by;
    }

    /**
     * Returns a formatted string representation of the deadline task
     * to be stored in a file.
     *
     * @return The string representation of the task in file format.
     */
    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A string containing the task type indicator, status, description, and due date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
