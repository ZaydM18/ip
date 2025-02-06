package monty;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MontyTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testMontyInitialization() {
        Monty monty = new Monty();
        assertTrue(monty != null, "Monty should initialize correctly");
    }

    @Test
    void testMontyProcessesCommands() {
        String input = "todo Read Book\nbye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Monty monty = new Monty();
        monty.run();

        System.setIn(System.in);
        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Got it. I've added this task"), "Monty should process todo command");
        assertTrue(output.contains("Bye. Hope to see you again soon!"), "Monty should exit after 'bye' command");
    }

    @Test
    void testMontyExitsCorrectly() {
        String input = "bye\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Monty monty = new Monty();
        monty.run();

        System.setIn(System.in);
        System.setOut(originalOut);

        String output = outContent.toString();
        assertTrue(output.contains("Bye. Hope to see you again soon!"), "Monty should exit when 'bye' is entered");
    }
}
