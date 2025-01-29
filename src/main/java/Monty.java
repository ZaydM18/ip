import java.util.Scanner;

public class Monty {
    private static void printDash() {
        System.out.println("____________________________________________________________");
    }
    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int taskCount = 0;

        printDash();
        System.out.println(" Hello! I'm Monty, your personal organisational assistant! If you need a list, I'll be the first to enlist!");
        System.out.println(" What can I do for you?");
        printDash();

        Scanner sc = new Scanner(System.in);

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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                printDash();
            }

            else {
                Task newTask = new Task(userInput);
                tasks[taskCount] = newTask;
                taskCount++;

                printDash();
                System.out.println(" added: " + newTask.getDescription());

                if (userInput.toLowerCase().startsWith("read")) {
                    System.out.println(" (Oh good, I thought nobody liked to read anymore!)");
                }

                printDash();
            }

        }

    }
}
