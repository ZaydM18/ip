package monty.parser;

import monty.exception.MontyException;
import monty.task.Task;
import monty.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    private ArrayList<Task> tasks;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        tasks = new ArrayList<>();
        ui = new Ui();
    }

    @Test
    public void testEmptyTodoThrowsException() {
        MontyException exception = assertThrows(MontyException.class, () ->
                Parser.processCommand("todo", tasks, ui)
        );
        assertEquals(
                "Huh? You just left that description blank, friend. How can one make a list with this?".trim(),
                exception.getMessage().trim()
        );
    }

    @Test
    public void testInvalidCommandThrowsException() {
        MontyException exception = assertThrows(MontyException.class, () ->
                Parser.processCommand("invalidcommand", tasks, ui)
        );
        assertEquals(
                "What are you saying? Please tell me again. I must add it to the list!".replaceAll("\\s+", " ").trim(),
                exception.getMessage().replaceAll("\\s+", " ").trim()
        );
    }
}
