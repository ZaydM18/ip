package monty.parser;

import monty.exception.MontyException;
import monty.storage.Storage;
import monty.task.*;
import monty.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Parses and processes user commands, executing the appropriate actions.
 * This class handles task management operations such as adding, deleting, marking,
 * unmarking, and finding tasks, as well as interacting with the user interface.
 */
public class Parser {

    /**
     * Processes the user input and executes the corresponding command.
     *
     * @param userInput The input string entered by the user.
     * @param tasks The list of tasks currently stored.
     * @param ui The user interface for displaying messages.
     * @throws MontyException If an invalid command is entered or required arguments are missing.
     */
    public static void processCommand(String userInput, ArrayList<Task> tasks, Ui ui) throws MontyException {
        String[] words = userInput.split(" ", 2);
        String command = words[0];
        String argument = words.length > 1 ? words[1].trim() : "";

        switch (command) {
        case "bye": {
            ui.showGoodbye();
            Storage.saveTasks(tasks);
            return;
        }

        case "list": {
            ui.showTaskList(tasks);
            break;
        }

        case "mark": {
            int markIndex = validateTaskIndex(argument, tasks.size());
            tasks.get(markIndex).markAsDone();
            ui.showTaskMarked(tasks.get(markIndex));
            Storage.saveTasks(tasks);
            break;
        }

        case "unmark": {
            int unmarkIndex = validateTaskIndex(argument, tasks.size());
            tasks.get(unmarkIndex).markAsNotDone();
            ui.showTaskUnmarked(tasks.get(unmarkIndex));
            Storage.saveTasks(tasks);
            break;
        }

        case "todo": {
            if (argument.isEmpty()) {
                throw new MontyException("Huh? You just left that description blank, friend. How can one make a list with this?");
            }

            Task newToDo = new ToDo(argument);
            tasks.add(newToDo);
            ui.showTaskAdded(newToDo, tasks.size());
            Storage.saveTasks(tasks);
            break;
        }

        case "deadline": {
            if (!argument.contains(" /by ")) {
                throw new MontyException("Deadlines must include a '/by' followed by a date and time (yyyy-MM-dd HHmm).");
            }

            String[] deadlineParts = argument.split(" /by ", 2);
            Task newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
            tasks.add(newDeadline);
            ui.showTaskAdded(newDeadline, tasks.size());
            Storage.saveTasks(tasks);
            break;
        }

        case "event": {
            if (!argument.contains(" /from ") || !argument.contains(" /to ")) {
                throw new MontyException("Events must include '/from' and '/to' with a date and time (yyyy-MM-dd HHmm).");
            }

            String[] eventParts = argument.split(" /from | /to ", 3);
            Task newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
            tasks.add(newEvent);
            ui.showTaskAdded(newEvent, tasks.size());
            Storage.saveTasks(tasks);
            break;
        }

        case "date": {
            processDateCommand(argument, tasks, ui);
            break;
        }

        case "delete": {
            int deleteIndex = validateTaskIndex(argument, tasks.size());
            Task removedTask = tasks.remove(deleteIndex);
            ui.showTaskDeleted(removedTask, tasks.size());
            Storage.saveTasks(tasks);
            break;
        }

        case "find": {
            processFindCommand(argument, tasks, ui);
            break;
        }

        case "sort": {
            if (argument.equals("deadline")) {
                tasks.sort((task1, task2) -> {
                    if (task1 instanceof Deadline && task2 instanceof Deadline) {
                        return Deadline.comparator.compare((Deadline) task1, (Deadline) task2);
                    }
                    return 0;
                });
                ui.showSortedTasks(tasks, "Deadlines sorted chronologically.");
            } else if (argument.equals("event")) {
                tasks.sort((task1, task2) -> {
                    if (task1 instanceof Event && task2 instanceof Event) {
                        return Event.comparator.compare((Event) task1, (Event) task2);
                    }
                    return 0;
                });
                ui.showSortedTasks(tasks, "Events sorted chronologically.");
            } else if (argument.equals("todo")) {
                tasks.sort((task1, task2) -> {
                    if (task1 instanceof ToDo && task2 instanceof ToDo) {
                        return ToDo.comparator.compare((ToDo) task1, (ToDo) task2);
                    }
                    return 0;
                });
                ui.showSortedTasks(tasks, "Todos sorted alphabetically.");
            } else {
                throw new MontyException("Invalid sort type. Use: sort deadline, sort event, or sort todo.");
            }
            Storage.saveTasks(tasks);
            break;

        }



        default: {
                throw new MontyException("What are you saying? Please tell me again. I must add it to the list!");
            }
        }
    }

    private static int validateTaskIndex(String argument, int size) throws MontyException {
        if (argument.isEmpty()) {
            throw new MontyException(" Your task number is out of range!");
        }

        int index = Integer.parseInt(argument) - 1;

        if (index < 0 || index >= size) {
            throw new MontyException(" Your task number is out of range!");
        }

        return index;
    }

    private static void processDateCommand(String argument, ArrayList<Task> tasks, Ui ui) throws MontyException {
        if (argument.isEmpty()) {
            throw new MontyException(" Please provide a date in yyyy-MM-dd format.");
        }

        try {
            LocalDate targetDate = LocalDate.parse(argument, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks) {
                if (task instanceof Deadline && ((Deadline) task).getDate().toLocalDate().equals(targetDate)) {
                    matchingTasks.add(task);
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    if (e.getStartDate().toLocalDate().equals(targetDate) || e.getEndDate().toLocalDate().equals(targetDate)) {
                        matchingTasks.add(task);
                    }
                }
            }

            if (matchingTasks.isEmpty()) {
                ui.showNoTasksFoundForDate();
            } else {
                ui.showTasksForDate(targetDate, matchingTasks);
            }
        } catch (DateTimeParseException e) {
            throw new MontyException(" Invalid date format! Please use yyyy-MM-dd.");
        }
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for.
     * @param tasks   The list of tasks to search in.
     * @param ui      The UI component to display results.
     */
    private static void processFindCommand(String keyword, ArrayList<Task> tasks, Ui ui) throws MontyException {
        if (keyword.isEmpty()) {
            throw new MontyException("You must provide a keyword to search for.");
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showError("No matching tasks found.");
            return;
        }

        ui.showFoundTasks(matchingTasks.toArray(new Task[0]));
    }

}
