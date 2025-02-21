package monty.storage;

import monty.task.Task;
import monty.task.ToDo;
import monty.task.Deadline;
import monty.task.Event;
import monty.exception.MontyException;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;

/**
 * Handles saving and loading of tasks to and from a file.
 */
public class Storage {
    private static final String FILE_PATH = "./data/monty.txt";
    private static final String DIRECTORY_PATH = "./data";

    /**
     * Saves the list of tasks to a file.
     *
     * @param tasks The list of tasks to be saved.
     * @throws MontyException If there is an error writing to the file.
     */
    public static void saveTasks(ArrayList<Task> tasks) throws MontyException {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
        } catch (IOException e) {
            throw new MontyException("Error saving tasks.");
        }
    }

    /**
     * Loads the list of tasks from a file.
     *
     * @return The list of tasks read from the file.
     * @throws MontyException If there is an error reading the file.
     */
    public static ArrayList<Task> loadTasks() throws MontyException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);

        assert FILE_PATH != null : "File path should not be null";

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            throw new MontyException("Error loading tasks.");
        }

        assert tasks != null : "Task list should not be null after loading";
        assert tasks.size() >= 0 : "Task list should never have a negative size";

        return tasks;
    }

    /**
     * Parses a task from a line of text.
     *
     * @param line The line containing task data.
     * @return The parsed Task object.
     * @throws MontyException If the line is corrupted or has an invalid format.
     */
    private static Task parseTask(String line) throws MontyException {
        String[] parts = line.split(" \\| ");

        if (parts.length < 3) {
            throw new MontyException("Corrupted task data: " + line);
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        try {
            Task task;
            switch (type) {
                case "T":
                    task = new ToDo(description);
                    break;
                case "D":
                    task = new Deadline(description, parts[3]);
                    break;
                case "E":
                    task = new Event(description, parts[3], parts[4]);
                    break;
                default:
                    throw new MontyException("Unknown task type in data: " + type);
            }

            task.setDone(isDone);
            return task;
        } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
            throw new MontyException("Corrupted task data: " + line);
        }
    }
}
