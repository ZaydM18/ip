package monty.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ToDoTest {
    @Test
    public void testToDoCreation() {
        ToDo todo = new ToDo("Buy groceries");
        assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    public void testMarkAsDone() {
        ToDo todo = new ToDo("Read book");
        todo.markAsDone();
        assertEquals("[T][X] Read book", todo.toString());
    }
}
