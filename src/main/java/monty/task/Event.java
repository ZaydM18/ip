package monty.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
    }

    private LocalDateTime parseDateTime(String dateTime) {
        try {
            return LocalDateTime.parse(dateTime, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(" Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2024-05-30 1800).");
        }
    }

    public LocalDateTime getStartDate() {
        return from;
    }

    public LocalDateTime getEndDate() {
        return to;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(INPUT_FORMAT) + " | " + to.format(INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}

