package model;

import java.util.ArrayList;
import java.util.List;

public class Day {

    private double availHours;
    private List<Task> tasks;

    public Day(double availHours) {
        this.availHours = availHours;
        tasks = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public double getAvailHours() {
        return availHours;
    }

    public void setAvailHours(double availHours) {
        this.availHours = availHours;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void addPartialTask(Task task, double len) {
        task.setLenPerDay(len);
        tasks.add(task);
    }

    public void subtractHours(double hoursUsed) {
        availHours -= hoursUsed;
    }

    public String toString() {
        int hours = (int) availHours;
        int minutes = (int) Math.round((availHours - hours) * 60);
        return "Available hours: " + hours + " hours " + minutes + " minutes";
    }



}
