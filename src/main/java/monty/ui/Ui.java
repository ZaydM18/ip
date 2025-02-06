package monty.ui;

import monty.task.Task;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {
    private final Scanner sc;

    public Ui() {
        this.sc = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Monty");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void close() {
        sc.close();
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println("  Now you have " + size + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println("  Now you have " + size + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    public void showTaskList(java.util.List<Task> tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
        }
    }

    public void showNoTasksFoundForDate() {
        System.out.println(" No deadlines or events found on this date. Maybe you should make some?");
    }

    public void showTasksForDate(LocalDate date, java.util.List<Task> tasks) {
        System.out.println(" Here are the deadlines and events on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        for (Task task : tasks) {
            System.out.println("  " + task);
        }
    }
}
