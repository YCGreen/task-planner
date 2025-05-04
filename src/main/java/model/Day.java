package model;

import java.util.HashMap;
import java.util.Map;

public class Day {

    private double availHours;
    private Map<Task, Double> tasks;

    public Day(double availHours) {
        this.availHours = availHours;
        tasks = new HashMap<>();
    }

    public Map<Task, Double> getTasks() {
        return tasks;
    }

    public double getAvailHours() {
        return availHours;
    }

    public void setAvailHours(double availHours) {
        this.availHours = availHours;
    }

    public void addTask(Task task) {
        tasks.put(task, task.getLen());
        subtractHours(task.getLen());
    }

    public void addPartialTask(Task task, double len) {
        task.setLenPerDay(len);
        tasks.put(task, len);
        subtractHours(len);
    }

    public void subtractHours(double hoursUsed) {
        availHours -= hoursUsed;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Task, Double> entry : tasks.entrySet()) {
            sb.append(entry.getKey().toString() + ": " + formatHours(entry.getValue()) + "\n");
        }
        return sb.toString();
    }

    private String formatHours(double time) {
        int hours = (int) time;
        int minutes = (int) Math.round((time - hours) * 60);
        if(hours < 1) {
            return minutes + " minutes";
        }
        if(minutes < 1) {
            return hours + " hours";
        }
        return hours + " hours " + minutes + " minutes";
    }
}


