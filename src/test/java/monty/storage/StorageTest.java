package monty.storage;

import monty.task.Task;
import monty.task.ToDo;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @Test
    public void testSaveAndLoadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("Test saving"));

        Storage.saveTasks(tasks);
        ArrayList<Task> loadedTasks = Storage.loadTasks();

        assertFalse(loadedTasks.isEmpty());
        assertEquals("[T][ ] Test saving", loadedTasks.get(0).toString());
    }
}
