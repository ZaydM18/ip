import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Monty {
    private final Ui ui;
    private final ArrayList<Task> tasks;

    public Monty() {
        this.ui = new Ui();
        this.tasks = Storage.loadTasks();
    }

    public void run() {
        ui.showWelcome();
        while (true) {
            String userInput = ui.readCommand();
            if (userInput.equals("bye")) break;
            Parser.processCommand(userInput, tasks, ui);
        }
        ui.showGoodbye();
        ui.close();
    }

    public static void main(String[] args) {
        new Monty().run();
    }
}
