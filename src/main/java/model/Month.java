package model;

import java.util.ArrayList;
import java.time.*;
import java.util.HashMap;
import java.util.List;

public class Month {

    private HashMap<LocalDate, Task> days = new HashMap<LocalDate, Task>();

    public Month(int availHours, LocalDate date) {
        for (int i = 1; i <= date.lengthOfMonth(); i++) {
            days.add(new Day(availHours, date));
        }
    }

    public List<Day> getDays() {
        return days;
    }

    public List<Day> getDaysIx(LocalDate begin, LocalDate end) {
        return days.subList(begin, end);
    }

    public Day getDay(int index) {
        return days.get(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < days.size(); i++) {
            sb.append("model.Day " + (i + 1) + ": " + days.get(i).toString() + "\n");
        }

        return sb.toString();
    }
}


/*
On home screen: create account. username, password
Options: View calendar, set availability, add new task (future: delete existing task)
View calendar: displays all tasks on a week-by-week or by month
Set availability: set how many hours per day to work on all tasks (by week basis) Future: set actual hours eg 9-10 PM
    maybe just set standard hours per day you have available and then if there are any abnormal days set those
Add new task: task name, amount of time to complete, deadline. option: split up in all available days
or do in shorter number of days with more work per day? maybe set that in set availability
    Enter. then task scheduler schedules and leads to view calendar

 */