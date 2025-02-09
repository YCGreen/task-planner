package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Scheduler {

    private ArrayList<Month> months;

    public Scheduler(int availHours) {
        months = new ArrayList<>();
        LocalDate currMonth = LocalDate.now();

        for (int i = 0; i < 12; i++) {
            currMonth = currMonth.plusMonths(1);
            months.add(new Month(availHours, currMonth));
        }
    }


    public boolean addTask(Task task, LocalDate dueDate) {
        LocalDate now = LocalDate.now();
        int days = now.until(dueDate).getDays();





        while (!days.get(currDay).addTask(task)) {
            currDay++;
        }

        return task.getStatus() == AssignStatus.COMPLETE;
    }
}
