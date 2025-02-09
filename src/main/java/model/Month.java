package model;

import java.util.ArrayList;
import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Month {

    private HashMap<LocalDate, Day> days = new HashMap<LocalDate, Day>();

    public Month(int availHours, LocalDate date) {
        java.time.Month currentMonth = date.getMonth();

        while (date.getMonth() == currentMonth) {
            days.put(date, new Day(availHours));
            date = date.plusDays(1);
        }
    }

    public HashMap<LocalDate, Day> getDays() {
        return days;
    }

    public List<Day> getDaysInRange(LocalDate begin, LocalDate end) {
        return days.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(begin) && !entry.getKey().isAfter(end))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
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