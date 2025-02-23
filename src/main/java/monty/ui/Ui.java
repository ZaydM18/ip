package monty.ui;

import monty.task.Task;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Handles user interactions, including displaying messages and reading user input.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Constructs a new Ui instance and initializes the scanner for reading input.
     */
    public Ui() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the program starts.
     */
    public void showWelcome() {
        showLine();
        System.out.println(" Strange... I find myself in this new bo- Hello! I'm Monty!");
        System.out.println(" What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the program exits.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon! Don't keep me waiting...");
    }

    /**
     * Displays a horizontal line for formatting.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
    }

    /**
     * Reads and returns the user's input command.
     *
     * @return The trimmed user input.
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Closes the scanner to prevent resource leaks.
     */
    public void close() {
        sc.close();
    }

    /**
     * Displays a message indicating that a task has been added.
     *
     * @param task The task that was added.
     * @param size The updated number of tasks in the list.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println("  Now you have " + size + " tasks in the list. What will you do about them?");
    }

    /**
     * Displays a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The updated number of tasks in the list.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println("  Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
        System.out.println(" Impressive, you're more powerful than I expected.");
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param task The task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
        System.out.println(" It's not like you to leave business unfinished...");
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public void showTaskList(java.util.List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays a message when no deadlines or events are found for a given date.
     */
    public void showNoTasksFoundForDate() {
        System.out.println(" No deadlines or events found on this date. Maybe you should make some?");
    }

    /**
     * Displays the deadlines and events scheduled for a given date.
     *
     * @param date  The date to check for tasks.
     * @param tasks The list of tasks scheduled for that date.
     */
    public void showTasksForDate(LocalDate date, java.util.List<Task> tasks) {
        System.out.println(" Here are the deadlines and events on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        for (Task task : tasks) {
            System.out.println("  " + task);
        }
    }

    /**
     * Displays the tasks that match the given keyword search.
     *
     * @param matchingTasks The list of tasks that contain the search keyword.
     */
    public void showFoundTasks(Task... matchingTasks) {
        if (matchingTasks.length == 0) {
            System.out.println("🔍 No matching tasks found.");
        } else {
            System.out.println("🔍 Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.length; i++) {
                System.out.println((i + 1) + ". " + matchingTasks[i]);
            }
        }
    }

    /**
     * Displays the sorted list of tasks with an appropriate message.
     *
     * @param tasks   The list of tasks that have been sorted.
     * @param message A message indicating the type of sorting performed.
     */
    public void showSortedTasks(ArrayList<Task> tasks, String message) {
        System.out.println("🔽 " + message);
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks to display.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays a general message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a help message with a link for users to visit if they need assistance.
     * The message is indirectly printed to the console.
     */
    public void showHelp() {
        this.showMessage("For a full list of commands, visit: https://zaydm18.github.io/ip/");
    }



}
