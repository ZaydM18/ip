package monty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;


/**
 * Represents an event task with a specific start and end time.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description The description of the event.
     * @param from        The start date and time in "yyyy-MM-dd HHmm" format.
     * @param to          The end date and time in "yyyy-MM-dd HHmm" format.
     * @throws IllegalArgumentException If the date format is incorrect.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
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
            throw new IllegalArgumentException(
                    " Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2024-05-30 1800).");
        }
    }

    /**
     * Returns the start date and time of the event.
     *
     * @return The start date as a {@code LocalDateTime} object.
     */
    public LocalDateTime getStartDate() {
        return from;
    }

    /**
     * Returns the end date and time of the event.
     *
     * @return The end date as a {@code LocalDateTime} object.
     */
    public LocalDateTime getEndDate() {
        return to;
    }

    /**
     * Comparator for sorting {@code Event} tasks chronologically by start date.
     */
    public static final Comparator<Event> comparator = Comparator.comparing(Event::getStartDate);

    /**
     * Returns a formatted string representation of the event task
     * to be stored in a file.
     *
     * @return The string representation of the task in file format.
     */
    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | "
                + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return A string containing the task type indicator, status, description, start time, and end time.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
