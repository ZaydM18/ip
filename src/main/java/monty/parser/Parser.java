package monty.parser;

import monty.exception.MontyException;
import monty.storage.Storage;
import monty.task.*;
import monty.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Parses and processes user commands, executing the appropriate actions.
 * This class handles task management operations such as adding, deleting, marking,
 * unmarking, and finding tasks, as well as interacting with the user interface.
 */
public class Parser {

    private static Task pendingDeletionTask = null;

    /**
     * Processes the user input and executes the corresponding command.
     *
     * @param userInput The input string entered by the user.
     * @param tasks     The list of tasks currently stored.
     * @param ui        The user interface for displaying messages.
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
                throw new MontyException(
                        "Huh? You just left that description blank, friend. How can one make a list with this?");
            }

            Task newToDo = new ToDo(argument);
            tasks.add(newToDo);
            ui.showTaskAdded(newToDo, tasks.size());
            Storage.saveTasks(tasks);
            break;
        }

        case "deadline": {
            if (!argument.contains(" /by ")) {
                throw new MontyException(
                        "Deadlines must include a '/by' followed by a date and time (yyyy-MM-dd HHmm).");
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
                throw new MontyException(
                        "Events must include '/from' and '/to' with a date and time (yyyy-MM-dd HHmm).");
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
            if (argument.isEmpty()) {
                throw new MontyException("Please specify a task number to delete.");
            }

            int deleteIndex = validateTaskIndex(argument, tasks.size());
            pendingDeletionTask = tasks.get(deleteIndex);

            ui.showMessage("Are you sure you want to delete this task?\n"
                    + pendingDeletionTask + "\nType 'confirm delete' to proceed.");
            break;
        }

        case "find": {
            processFindCommand(argument, tasks, ui);
            break;
        }

        case "sort": {
            processSortCommand(tasks, ui);
            break;
        }

        case "clear": {
            if (!argument.isEmpty()) {
                throw new MontyException("To delete specific tasks, use the delete command (e.g., delete 1).");
            }

            ui.showMessage("⚠ Are you sure you want to clear all tasks? "
                    + "Don't just erase my precious list willy-nilly! "
                    + "Type 'confirm clear' to proceed.");

            break;
        }

        case "confirm": {
            if (argument.equals("clear")) {
                tasks.clear();
                Storage.saveTasks(tasks);
                ui.showMessage("✅ All tasks have been cleared!");
            } else if (argument.equals("delete")) {
                if (pendingDeletionTask != null) {
                    tasks.remove(pendingDeletionTask);
                    Storage.saveTasks(tasks);
                    ui.showTaskDeleted(pendingDeletionTask, tasks.size());
                    pendingDeletionTask = null;
                } else {
                    throw new MontyException("No pending task deletion found.");
                }
            } else {
                throw new MontyException("Invalid confirmation command.");
            }
            break;
        }

            case "help": {
                ui.showHelp();
                break;
            }

            default: {
            throw new MontyException(
                    "Sorry, did you say that right? If not, please, do correct yourself! The list must expand!");
        }

        }
    }

    private static int validateTaskIndex(String argument, int size) throws MontyException {
        if (argument.isEmpty()) {
            throw new MontyException("You must specify a task number.");
        }

        int index;
        try {
            index = Integer.parseInt(argument.trim());
        } catch (NumberFormatException e) {
            throw new MontyException("Invalid task number! Please enter a valid integer.");
        }

        if (index <= 0 || index > size) {
            throw new MontyException("Task number must be between 1 and " + size + ".");
        }

        return index - 1;
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
                    if (e.getStartDate().toLocalDate().equals(targetDate)
                            || e.getEndDate().toLocalDate().equals(targetDate)) {
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

    /**
     * Sorts tasks by first grouping them into categories (Todo, Deadline, Event),
     * then sorting within those groups, and finally merging them in order:
     * ToDo → Deadline → Event.
     *
     * @param tasks The list of tasks to be sorted.
     * @param ui The UI component to display the sorted list.
     */
    private static void processSortCommand(ArrayList<Task> tasks, Ui ui) {
        ArrayList<ToDo> todos = new ArrayList<>();
        ArrayList<Deadline> deadlines = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof ToDo) {
                todos.add((ToDo) task);
            } else if (task instanceof Deadline) {
                deadlines.add((Deadline) task);
            } else if (task instanceof Event) {
                events.add((Event) task);
            }
        }

        todos.sort(ToDo.comparator);
        deadlines.sort(Deadline.comparator);
        events.sort(Event.comparator);

        tasks.clear();
        tasks.addAll(todos);
        tasks.addAll(deadlines);
        tasks.addAll(events);

        ui.showSortedTasks(tasks, "Tasks sorted: ToDo → Deadline → Event. "
                + "An organised list is the best list!");

        try {
            Storage.saveTasks(tasks);
        } catch (MontyException e) {
            ui.showError("⚠ Error saving sorted tasks.");
        }
    }



}
