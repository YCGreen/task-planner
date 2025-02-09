package model;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private ArrayList<Month> months;

    public Scheduler(int availHours) {
        months = new ArrayList<>();
        LocalDate currMonth = LocalDate.now().withDayOfYear(1);

        for (int i = 0; i < 12; i++) {
            currMonth = currMonth.plusMonths(1);
            months.add(new Month(availHours, currMonth));
        }
    }

    public boolean addTask(Task task, LocalDate dueDate) {
        LocalDate now = LocalDate.now();

        int monthStart = now.getMonthValue() - 1;
        int monthDue = dueDate.getMonthValue() - 1;

        List<Day> days = new ArrayList<>();

        if(monthStart - monthDue < 0) {
            System.out.println("Invalid: due date before start date");
            return false;
        } else if(dueDate.isAfter(now.withDayOfYear(LocalDate.now().lengthOfYear()))) {
            addYear();
        } else if(monthStart == monthDue) {
            days = months.get(monthStart).getDaysInRange(now, dueDate); //TODO: fix for multiple years in months
        } else if(monthStart < monthDue) {
            int currMonth = monthStart;
            for (int i = 0; i < monthDue - monthStart; i++) {
                days.addAll(months.get(currMonth).getDaysInRange(now, dueDate));
            }
        }

        //now add task
        //this is silly. find way to do it in day.


        while (!days.get(currDay).addTask(task)) {
            currDay++;
        }

        return task.getStatus() == AssignStatus.COMPLETE;
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

    private void addYear() {
        LocalDate currMonth = LocalDate.now().withDayOfYear(1).plusYears(1);

        for (int i = 0; i < 12; i++) {
            currMonth = currMonth.plusMonths(1);
            months.add(new Month(3, currMonth)); //TODO: replace availHours
        }
    }

}
