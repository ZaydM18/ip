package monty.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testTaskMarkAsDone() {
        Task task = new ToDo("Complete homework");
        assertFalse(task.isDone);
        task.markAsDone();
        assertTrue(task.isDone);
    }

    @Test
    public void testTaskMarkAsNotDone() {
        Task task = new ToDo("Read book");
        task.markAsDone();
        task.markAsNotDone();
        assertFalse(task.isDone);
    }

    @Test
    public void testTaskToString() {
        Task task = new ToDo("Exercise");
        assertEquals("[T][ ] Exercise", task.toString());
        task.markAsDone();
        assertEquals("[T][X] Exercise", task.toString());
    }
}
