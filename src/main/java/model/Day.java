package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private int availHours;
    private List<Task> tasks = new ArrayList<>();

    public Day(int availHours) {
        this.availHours = availHours;
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

        for(Task task : tasks) {
            sb.append(task.toString() + "\n");
        }

        return sb.toString();
    }

}
