package monty.parser;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


import monty.exception.MontyException;
import monty.storage.Storage;
import monty.task.Deadline;
import monty.task.Event;
import monty.task.Task;
import monty.task.ToDo;
import monty.ui.Ui;

public class Parser {
    public static void processCommand(String userInput, ArrayList<Task> tasks, Ui ui) throws MontyException {
        String[] words = userInput.split(" ", 2);
        String command = words[0];
        String argument = words.length > 1 ? words[1].trim() : "";

        switch (command) {
            case "bye":
                ui.showGoodbye();
                Storage.saveTasks(tasks);
                return;

            case "list":
                ui.showTaskList(tasks);
                break;

            case "mark":
                int markIndex = validateTaskIndex(argument, tasks.size());
                tasks.get(markIndex).markAsDone();
                ui.showTaskMarked(tasks.get(markIndex));
                Storage.saveTasks(tasks);
                break;

            case "unmark":
                int unmarkIndex = validateTaskIndex(argument, tasks.size());
                tasks.get(unmarkIndex).markAsNotDone();
                ui.showTaskUnmarked(tasks.get(unmarkIndex));
                Storage.saveTasks(tasks);
                break;

            case "todo":
                if (argument.isEmpty()) {
                    throw new MontyException("Huh? You just left that description blank, friend. How can one make a list with this?");
                }
                Task newToDo = new ToDo(argument);
                tasks.add(newToDo);
                ui.showTaskAdded(newToDo, tasks.size());
                Storage.saveTasks(tasks);
                break;

            case "deadline":
                if (!argument.contains(" /by ")) {
                    throw new MontyException("Deadlines must include a '/by' followed by a date and time (yyyy-MM-dd HHmm).");
                }
                String[] deadlineParts = argument.split(" /by ", 2);
                Task newDeadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                tasks.add(newDeadline);
                ui.showTaskAdded(newDeadline, tasks.size());
                Storage.saveTasks(tasks);
                break;

            case "event":
                if (!argument.contains(" /from ") || !argument.contains(" /to ")) {
                    throw new MontyException("Events must include '/from' and '/to' with a date and time (yyyy-MM-dd HHmm).");
                }
                String[] eventParts = argument.split(" /from | /to ", 3);
                Task newEvent = new Event(eventParts[0], eventParts[1], eventParts[2]);
                tasks.add(newEvent);
                ui.showTaskAdded(newEvent, tasks.size());
                Storage.saveTasks(tasks);
                break;

            case "date":
                processDateCommand(argument, tasks, ui);
                break;

            case "delete":
                int deleteIndex = validateTaskIndex(argument, tasks.size());
                Task removedTask = tasks.remove(deleteIndex);
                ui.showTaskDeleted(removedTask, tasks.size());
                Storage.saveTasks(tasks);
                break;

            default:
                throw new MontyException("What are you saying? Please tell me again. I must add it to the list!");
        }
    }

    private static int validateTaskIndex(String argument, int size) throws MontyException {
        if (argument.isEmpty()) {
            throw new MontyException("Your task number is out of range!");
        }
        int index = Integer.parseInt(argument) - 1;
        if (index < 0 || index >= size) {
            throw new MontyException("Your task number is out of range!");
        }
        return index;
    }

    private static void processDateCommand(String argument, ArrayList<Task> tasks, Ui ui) throws MontyException {
        if (argument.isEmpty()) {
            throw new MontyException("Please provide a date in yyyy-MM-dd format.");
        }

        try {
            LocalDate targetDate = LocalDate.parse(argument, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks) {
                if (task instanceof Deadline && ((Deadline) task).getDate().toLocalDate().equals(targetDate)) {
                    matchingTasks.add(task);
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    if (event.getStartDate().toLocalDate().equals(targetDate) || event.getEndDate().toLocalDate().equals(targetDate)) {
                        matchingTasks.add(task);
                    }
                }
            }

            if (matchingTasks.isEmpty()) {
                ui.showNoTasksFoundForDate();
                return;
            }
            ui.showTasksForDate(targetDate, matchingTasks);
        } catch (DateTimeParseException e) {
            throw new MontyException("Invalid date format! Please use yyyy-MM-dd.");
        }
    }
}
