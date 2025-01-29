import java.util.Scanner;

public class Monty {
    private static void printDash() {
        System.out.println("____________________________________________________________");
    }
    public static void main(String[] args) {
        Task[] tasks = new Task[100]; 
        int taskCount = 0;

        Scanner sc = new Scanner(System.in);

        printDash();
        System.out.println(" Hello! I'm Monty");
        System.out.println(" What can I do for you?");
        printDash();

        while (true) {
            String userInput = sc.nextLine().trim();

            if (userInput.equalsIgnoreCase("bye")) {
                printDash();
                System.out.println(" Bye. Hope to see you again soon!");
                printDash();
                break;
            }
            else if (userInput.equalsIgnoreCase("list")) {
                printDash();
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                printDash();
            }
            else if (userInput.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(5)) - 1;
                tasks[taskNumber].markAsDone();
                printDash();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskNumber]);
                printDash();
            }
            else if (userInput.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(userInput.substring(7)) - 1;
                tasks[taskNumber].markAsNotDone();
                printDash();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[taskNumber]);
                printDash();
            }
            else {
                tasks[taskCount] = new Task(userInput);
                taskCount++;

                printDash();
                System.out.println(" added: " + userInput);

                if (userInput.toLowerCase().startsWith("read")) {
                    System.out.println(" (Oh good, I thought nobody liked to read anymore!)");
                }

                printDash();
            }
        }

        sc.close();
    }
}
