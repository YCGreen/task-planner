package model;

import java.util.ArrayList;
import java.util.List;

public class Week {

    private List<Day> days = new ArrayList<Day>(); //make an array
    private int currDay = 0;

    public Week(int availHours) {
        for (int i = 0; i < 7; i++) {
            days.add(new Day(availHours));
        }
    }

    public boolean addTask(Task task) {
        while (!days.get(currDay).addTask(task)) {
            currDay++;
        }

        return task.getStatus() == AssignStatus.COMPLETE;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < days.size(); i++) {
            sb.append("model.Day " + i + ": " + days.get(i).toString() + "\n");
        }

        return sb.toString();
    }
}
