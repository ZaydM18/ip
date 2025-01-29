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
            try {
                String userInput = sc.nextLine().trim();
                String[] words = userInput.split(" ", 2);
                String command = words[0];
                String argument = words.length > 1 ? words[1].trim() : "";

                switch (command) {
                    case "bye":
                        printLine();
                        System.out.println(" Bye. Hope to see you again soon!");
                        printLine();
                        return;

                    case "list":
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < taskCount; i++) {
                            System.out.println("  " + (i + 1) + ". " + tasks[i]);
                        }
                        printLine();
                        break;

                    case "mark":
                        if (argument.isEmpty()) throw new MontyException(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
                        int markIndex = Integer.parseInt(argument) - 1;
                        if (markIndex < 0 || markIndex >= taskCount) throw new MontyException(" Your task number is out of range!");
                        tasks[markIndex].markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[markIndex]);
                        printLine();
                        break;

                    case "unmark":
                        if (argument.isEmpty()) throw new MontyException(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
                        int unmarkIndex = Integer.parseInt(argument) - 1;
                        if (unmarkIndex < 0 || unmarkIndex >= taskCount) throw new MontyException(" Your task number is out of range!");
                        tasks[unmarkIndex].markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[unmarkIndex]);
                        break;

                    case "todo":
                        if (argument.isEmpty()) throw new MontyException("Huh? You just left that description blank, friend. How can one make a list with this?");
                        tasks[taskCount++] = new ToDo(argument);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[taskCount - 1]);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        printLine();
                        break;

                    case "deadline":
                        if (!argument.contains(" /by ")) throw new MontyException("Deadlines must include a '/by' followed by a due date. Hopefully there's no deadline on how long I can keep making lists...");
                        String[] deadlineParts = argument.split(" /by ", 2);
                        tasks[taskCount++] = new Deadline(deadlineParts[0], deadlineParts[1]);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[taskCount - 1]);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        printLine();
                        break;

                    case "event":
                        if (!argument.contains(" /from ") || !argument.contains(" /to ")) throw new MontyException("Ah! Another error! MUST MAKE LIST! An event must include '/from' and '/to'");
                        String[] eventParts = argument.split(" /from | /to ", 3);
                        tasks[taskCount++] = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks[taskCount - 1]);
                        System.out.println(" Now you have " + taskCount + " tasks in the list.");
                        printLine();
                        break;

                    default:
                        throw new MontyException("What are you saying? Please tell me again. I must add it to the list!");
                }

            } catch (MontyException e) {
                printLine();
                System.out.println(" " + e.getMessage());
            } catch (NumberFormatException e) {
                printLine();
                System.out.println(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
            }
        }
        sc.close();
    }
}
