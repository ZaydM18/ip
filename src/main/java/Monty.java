import java.util.Scanner;
import java.util.ArrayList;

public class Monty {
    private static void printLine() {
        System.out.println("____________________________________________________________");
    }

    public static void main(String[] args) {
        ArrayList<Task> tasks = Storage.loadTasks();
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
                        System.out.println(" Bye. Hope to see you again soon!");
                        Storage.saveTasks(tasks);
                        printLine();
                        return;

                    case "list":
                        System.out.println(" Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("  " + (i + 1) + ". " + tasks.get(i));
                        }
                        break;

                    case "mark":
                        if (argument.isEmpty()) throw new MontyException(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
                        int markIndex = Integer.parseInt(argument) - 1;
                        if (markIndex < 0 || markIndex >= tasks.size()) throw new MontyException(" Your task number is out of range!");
                        tasks.get(markIndex).markAsDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks.get(markIndex));
                        Storage.saveTasks(tasks);
                        break;

                    case "unmark":
                        if (argument.isEmpty()) throw new MontyException(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
                        int unmarkIndex = Integer.parseInt(argument) - 1;
                        if (unmarkIndex < 0 || unmarkIndex >= tasks.size()) throw new MontyException(" Your task number is out of range!");
                        tasks.get(unmarkIndex).markAsNotDone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks.get(unmarkIndex));
                        Storage.saveTasks(tasks);
                        break;

                    case "todo":
                        if (argument.isEmpty()) throw new MontyException("Huh? You just left that description blank, friend. How can one make a list with this?");
                        tasks.add(new ToDo(argument));
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.get(tasks.size() - 1));
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        Storage.saveTasks(tasks);
                        break;

                    case "deadline":
                        if (!argument.contains(" /by ")) throw new MontyException("Deadlines must include a '/by' followed by a due date. Hopefully there's no deadline on how long I can keep making lists...");
                        String[] deadlineParts = argument.split(" /by ", 2);
                        tasks.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.get(tasks.size() - 1));
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        Storage.saveTasks(tasks);
                        break;

                    case "event":
                        if (!argument.contains(" /from ") || !argument.contains(" /to ")) throw new MontyException("Ah! Another error! MUST MAKE LIST! An event must include '/from' and '/to'");
                        String[] eventParts = argument.split(" /from | /to ", 3);
                        tasks.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.get(tasks.size() - 1));
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        Storage.saveTasks(tasks);
                        break;

                    case "delete":
                        if (argument.isEmpty()) throw new MontyException(" Your task number is out of range! How am I to keep track of my-- I mean, your -- beautiful list!");
                        int deleteIndex = Integer.parseInt(argument) - 1;
                        if (deleteIndex < 0 || deleteIndex >= tasks.size()) throw new MontyException(" Your task number is out of range!");
                        Task removedTask = tasks.remove(deleteIndex);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removedTask);
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        Storage.saveTasks(tasks);
                        break;

                    default:
                        printLine();
                        System.out.println(" What are you saying? Please tell me again. I must add it to the list!");
                        printLine();
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
