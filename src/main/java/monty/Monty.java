package monty;

import monty.parser.Parser;
import monty.storage.Storage;
import monty.task.Task;
import monty.ui.Ui;
import monty.exception.MontyException;
import java.util.ArrayList;

/**
 * The main class for the Monty task management application.
 * Handles user interactions, processes commands, and manages tasks.
 */
public class Monty {
    private final Ui ui;
    private final ArrayList<Task> tasks;

    /**
     * Initializes the Monty application.
     * Loads previously saved tasks from storage and initializes the user interface.
     */
    public Monty() {
        this.ui = new Ui();
        this.tasks = Storage.loadTasks();
    }

    /**
     * Runs the main loop of the Monty application.
     * Continuously reads and processes user input until the user exits the program.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String userInput = ui.readCommand();
            if (userInput.equals("bye")) {
                break;
            }

            try {
                Parser.processCommand(userInput, tasks, ui);
            } catch (MontyException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
        ui.close();
    }

    /**
     * The main entry point of the Monty application.
     * Starts the application by creating an instance of Monty and running it.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Monty().run();
    }
}
