package monty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private LocalDateTime by;

    public Deadline(String description, String by) {
        super(description);
        this.by = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(" Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2024-05-30 1800).");
        }
    }

    public LocalDateTime getDate() {
        return by;
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
