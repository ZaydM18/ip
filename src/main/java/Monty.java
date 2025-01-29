import java.util.Scanner;

public class Monty {
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;
        Scanner sc = new Scanner(System.in);

        printLine();
        System.out.println(" Hello! I'm Monty");
        System.out.println(" What can I do for you?");
        printLine();

        while (sc.hasNextLine()) {
            String userInput = sc.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                printLine();
                System.out.println(" Bye. Hope to see you again soon!");
                printLine();
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                printLine();
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                printLine();
            }
            else if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                tasks[taskNumber].markAsDone();
                printLine();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskNumber]);
                printLine();
            }
            else if (userInput.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                tasks[taskNumber].markAsNotDone();
                printLine();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[taskNumber]);
                printLine();
            }
            else if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5).trim();
                tasks[taskCount] = new ToDo(description);
                taskCount++;

                printLine();
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                printLine();
            }
            else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ", 2);
                String description = parts[0].trim();
                String by = parts.length > 1 ? parts[1].trim() : "No deadline specified";

                tasks[taskCount] = new Deadline(description, by);
                taskCount++;

                printLine();
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                printLine();
            }
            else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ", 3);
                String description = parts[0].trim();
                String from = parts.length > 1 ? parts[1].trim() : "Unknown start time";
                String to = parts.length > 2 ? parts[2].trim() : "Unknown end time";

                tasks[taskCount] = new Event(description, from, to);
                taskCount++;

                printLine();
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount - 1]);
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                printLine();
            }
            else {
                printLine();
                System.out.println("I love filling up lists as much as the next bot (provided that the next bot is not Duke!) but I do not support that command, sadly!");
                printLine();
            }
        }

        sc.close();
    }
}
