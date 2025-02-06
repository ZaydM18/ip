package monty.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UiTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ui ui;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ui = new Ui();
    }

    @Test
    public void testShowWelcomeMessage() {
        ui.showWelcome();
        String expectedOutput = "____________________________________________________________\n" +
                " Hello! I'm Monty\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void testShowLine() {
        ui.showLine();
        assertEquals("____________________________________________________________\n", outContent.toString());
    }

    @Test
    public void testShowError() {
        Ui ui = new Ui();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(outContent));

        ui.showError("Test error message");

        System.setOut(originalOut);

        String expectedOutput = "____________________________________________________________\n Test error message\n";
        assertEquals(expectedOutput, outContent.toString());
    }



    @Test
    public void testShowGoodbyeMessage() {
        ui.showGoodbye();
        String expectedOutput = " Bye. Hope to see you again soon!\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}
