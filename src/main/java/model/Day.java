package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {

    private int availHours;
    private List<Task> tasks = new ArrayList<>();
    private LocalDate date;

    public Day(int availHours, LocalDate date) {
        this.availHours = availHours;
        this.date = date;
    }

    public boolean addTask(Task task) {
        if (availHours <= 0) {
            return false;
        }

        tasks.add(task);

        if ((availHours -= task.getLen()) < 0) {
            task.begin();
            return false;
        }

        task.complete();

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(date.toString() + "\n");

        for(Task task : tasks) {
            sb.append(task.toString() + "\n");
        }

        return sb.toString();
    }

}
