package monty;

import monty.parser.Parser;
import monty.storage.Storage;
import monty.task.Task;
import monty.ui.Ui;
import monty.exception.MontyException;

import java.util.ArrayList;

/**
 * The main class for the Monty application, handling user interactions and task management.
 */
public class Monty {
    private final Ui ui;
    private final ArrayList<Task> tasks;

    /**
     * Initializes the Monty application by setting up the UI and loading tasks from storage.
     */
    public Monty() {
        this.ui = new Ui();

        ArrayList<Task> loadedTasks;
        try {
            loadedTasks = Storage.loadTasks();
        } catch (MontyException e) {
            this.ui.showError("Failed to load tasks. Starting with an empty task list.");
            loadedTasks = new ArrayList<>();
        }

        this.tasks = loadedTasks;
    }

    /**
     * Runs the main loop of the Monty application, processing user commands until termination.
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
     * The entry point for the Monty application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        new Monty().run();
    }
}