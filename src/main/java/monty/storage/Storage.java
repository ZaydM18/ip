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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {
    private static final String FILE_PATH = "./data/monty.txt";
    private static final String DIRECTORY_PATH = "./data";

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File(DIRECTORY_PATH);
            if (!dir.exists()) dir.mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : tasks) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(" Error saving tasks.");
        }
    }

    public static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return tasks;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println(" Error loading tasks.");
        }
        return tasks;
    }

    private static Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

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
                    System.out.println(" Warning: Corrupted line skipped: " + line);
                    return null;
            }

            task.setDone(isDone);
            return task;
        } catch (Exception e) {
            System.out.println(" Warning: Corrupted line skipped: " + line);
            return null;
        }
    }
}
