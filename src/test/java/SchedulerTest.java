import model.Task;
import model.Month;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SchedulerTest {

    @Test
    public void Scheduler() {
        Month month = new Month(3, 2025, 2);
        Task task1 = new Task("Task1", 2);
        Task task2 = new Task("Task2", 2);
        month.addTask(task1);
        month.addTask(task2);

        String expected = "model.Day 0: Task1, length: 2\n" +
                "Task2, length: 2\n" +
                "\n" +
                "model.Day 1: Task2, length: 2\n" +
                "\n" +
                "model.Day 2: \n" +
                "model.Day 3: \n" +
                "model.Day 4: \n" +
                "model.Day 5: \n" +
                "model.Day 6: \n";
        System.out.println(month.toString());
    }
}
