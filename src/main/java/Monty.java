import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
                        if (!argument.contains(" /by ")) throw new MontyException("Deadlines must include a '/by' followed by a date and time (yyyy-MM-dd HHmm).");
                        String[] deadlineParts = argument.split(" /by ", 2);
                        tasks.add(new Deadline(deadlineParts[0], deadlineParts[1]));
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.get(tasks.size() - 1));
                        Storage.saveTasks(tasks);
                        break;

                    case "event":
                        if (!argument.contains(" /from ") || !argument.contains(" /to ")) throw new MontyException("Events must include '/from' and '/to' with a date and time (yyyy-MM-dd HHmm).");
                        String[] eventParts = argument.split(" /from | /to ", 3);
                        tasks.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + tasks.get(tasks.size() - 1));
                        Storage.saveTasks(tasks);
                        break;

                    case "date":
                        if (argument.isEmpty()) throw new MontyException(" Please provide a date in yyyy-MM-dd format (e.g., 2024-06-01).");
                        try {
                            LocalDate targetDate = LocalDate.parse(argument, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            System.out.println(" Here are the deadlines and events on " + targetDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
                            boolean found = false;

                            for (Task task : tasks) {
                                if (task instanceof Deadline) {
                                    Deadline d = (Deadline) task;
                                    if (d.getDate().toLocalDate().equals(targetDate)) {
                                        System.out.println("  " + task);
                                        found = true;
                                    }
                                } else if (task instanceof Event) {
                                    Event e = (Event) task;
                                    if (e.getStartDate().toLocalDate().equals(targetDate) || e.getEndDate().toLocalDate().equals(targetDate)) {
                                        System.out.println("  " + task);
                                        found = true;
                                    }
                                }
                            }

                            if (!found) {
                                System.out.println(" No deadlines or events found on this date. Maybe you should make some? What do you mean I'm obsessed with lists?");
                            }
                        } catch (DateTimeParseException e) {
                            throw new MontyException(" Invalid date format! Please use yyyy-MM-dd (e.g., 2024-06-01). Don't go away! Make more lists!");
                        }
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
