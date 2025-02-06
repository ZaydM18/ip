package monty.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {
    @Test
    public void testDeadlineCreation() {
        Deadline deadline = new Deadline("Submit report", "2024-06-10 2359");
        assertEquals("[D][ ] Submit report (by: Jun 10 2024, 11:59 PM)", deadline.toString());
    }

    @Test
    public void testMarkAsDone() {
        Deadline deadline = new Deadline("Finish project", "2024-06-15 1200");
        deadline.markAsDone();
        assertEquals("[D][X] Finish project (by: Jun 15 2024, 12:00 PM)", deadline.toString());
    }
}
