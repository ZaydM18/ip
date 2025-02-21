package monty.ui;

import monty.Monty;
import monty.exception.MontyException;
import monty.parser.Parser;
import monty.storage.Storage;
import monty.task.Task;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;


/**
 * Controller for MainWindow. Provides the layout for the main user interface.
 */
public class MainWindow {
    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    @FXML
    private VBox dialogContainer;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane mainPane;

    private Monty monty;
    private ArrayList<Task> tasks;
    private Image userImage;
    private Image montyImage;

    /**
     * Initializes the UI styles when the application starts.
     * This method is automatically called after the FXML file is loaded.
     * It applies pixel font and text color styling
     * to ensure consistency across the chatbot interface.
     */
    @FXML
    public void initialize() {
        System.out.println("✅ Initialize method called. Checking styles...");

        dialogContainer.setStyle("-fx-background-color: transparent;");

        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        mainPane.layout();
    }

    /**
     * Initializes the application with a reference to Monty, its tasks, and user/chatbot images.
     */
    @FXML
    public void setMonty(Monty monty, Image userImage, Image montyImage) {
        assert monty != null : "Monty instance should not be null";
        assert userImage != null : "User image should not be null";
        assert montyImage != null : "Monty image should not be null";

        this.monty = monty;
        this.userImage = userImage;
        this.montyImage = montyImage;

        try {
            this.tasks = Storage.loadTasks();
            dialogContainer.getChildren().add(DialogBox
                    .getMontyDialog("📂 Loaded previous tasks successfully!", montyImage));
        } catch (MontyException e) {
            this.tasks = new ArrayList<>();
            dialogContainer.getChildren().add(DialogBox
                    .getMontyDialog("⚠ No saved tasks found. Starting fresh.", montyImage));
        }
    }

    /**
     * Handles user input when the send button is clicked.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response;
        try {
            StringBuilder capturedOutput = new StringBuilder();

            Ui guiUi = new Ui() {
                @Override
                public void showError(String message) {
                    capturedOutput.append("❌ ").append(message).append("\n");
                }

                @Override
                public void showTaskAdded(Task task, int size) {
                    capturedOutput.append("✔ Task added: ").append(task.toString()).append("\n")
                            .append("Now you have ").append(size).append(" tasks in the list.\n");
                }

                @Override
                public void showTaskDeleted(Task task, int size) {
                    capturedOutput.append("🗑 Task removed: ").append(task.toString()).append("\n")
                            .append("Now you have ").append(size).append(" tasks in the list.\n");
                }

                @Override
                public void showTaskMarked(Task task) {
                    capturedOutput.append("✅ Task marked as done: ").append(task.toString()).append("\n");
                }

                @Override
                public void showTaskUnmarked(Task task) {
                    capturedOutput.append("🔄 Task marked as not done: ").append(task.toString()).append("\n");
                }

                @Override
                public void showTaskList(java.util.List<Task> tasks) {
                    capturedOutput.append("📋 Here are the tasks in your list:\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        capturedOutput.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
                    }
                    capturedOutput.append("...\n");
                }

                @Override
                public void showNoTasksFoundForDate() {
                    capturedOutput
                            .append("📅 No deadlines or events found on this date. Maybe you should make some?\n");
                }

                @Override
                public void showTasksForDate(LocalDate date, java.util.List<Task> tasks) {
                    capturedOutput.append("📅 Here are the deadlines and events on ")
                            .append(date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))).append(":\n");
                    for (Task task : tasks) {
                        capturedOutput.append("  ").append(task).append("\n");
                    }
                }

                @Override
                public void showFoundTasks(Task... matchingTasks) {
                    if (matchingTasks.length == 0) {
                        capturedOutput.append("🔍 No matching tasks found.\n");
                    } else {
                        capturedOutput.append("🔍 Here are the matching tasks:\n");
                        for (int i = 0; i < matchingTasks.length; i++) {
                            capturedOutput.append((i + 1)).append(". ").append(matchingTasks[i]).append("\n");
                        }
                        capturedOutput.append("...\n");
                    }
                }

                @Override
                public void showSortedTasks(ArrayList<Task> tasks, String message) {
                    capturedOutput.append("🔽 ").append(message).append("\n");
                    for (int i = 0; i < tasks.size(); i++) {
                        capturedOutput.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
                    }
                }

                @Override
                public void showGoodbye() {
                    capturedOutput.append("👋 Bye. Hope to see you again soon!\n");
                }
            };

            Parser.processCommand(input, tasks, guiUi);
            Storage.saveTasks(tasks);
            response = capturedOutput.toString();

        } catch (IllegalArgumentException e) {
            response = "❌ Invalid date format! Please use yyyy-MM-dd HHmm.\n";
        } catch (MontyException e) {
            response = "❌ " + e.getMessage();
        } catch (Exception e) {
            response = "❌ An unexpected error occurred.";
            e.printStackTrace();
        }

        appendToDialog("You", input);
        appendToDialog("Monty", response);
        userInput.clear();
    }


    private void appendToDialog(String sender, String message) {
        DialogBox dialogBox;

        if (sender.equals("You")) {
            dialogBox = DialogBox.getUserDialog(message, userImage);
        } else {
            dialogBox = DialogBox.getMontyDialog(message, montyImage);
        }

        dialogContainer.getChildren().add(dialogBox);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }
}
