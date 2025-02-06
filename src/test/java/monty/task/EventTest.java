package monty.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    public void testEventCreation() {
        Event event = new Event("Team Meeting", "2024-06-05 1400", "2024-06-05 1600");
        assertEquals("[E][ ] Team Meeting (from: Jun 05 2024, 2:00 PM to: Jun 05 2024, 4:00 PM)", event.toString());
    }

    @Test
    public void testMarkAsDone() {
        Event event = new Event("Dinner Party", "2024-07-01 1900", "2024-07-01 2200");
        event.markAsDone();
        assertEquals("[E][X] Dinner Party (from: Jul 01 2024, 7:00 PM to: Jul 01 2024, 10:00 PM)", event.toString());
    }

    @Test
    public void testInvalidDateFormatThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Event("Invalid Event", "InvalidDate", "2024-07-01 2200");
        });
        assertEquals(" Invalid date format! Please use yyyy-MM-dd HHmm (e.g., 2024-05-30 1800).", exception.getMessage());
    }
}
