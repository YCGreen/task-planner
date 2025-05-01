package model;

import java.util.*;
import java.time.*;
import java.util.stream.Collectors;

public class Month {

    private TreeMap<LocalDate, Day> days = new TreeMap<LocalDate, Day>();
    private final int firstDay;
    private String monthName;

    public Month(double availHours, LocalDate date) {
        java.time.Month currentMonth = date.getMonth();

        monthName = currentMonth.toString();

        firstDay = date.withDayOfMonth(1).getDayOfWeek().getValue();

        while (date.getMonth() == currentMonth) {
            days.put(date, new Day(availHours));
            date = date.plusDays(1);
        }
    }

    public int getFirstDay() {
        return firstDay;
    }

    public TreeMap<LocalDate, Day> getDays() {
        return days;
    }

    public void setAvailHours(double availHours) {
        days.values().forEach(day -> day.setAvailHours(availHours));
    }

  /*  public List<Day> getDaysInRange(LocalDate begin, LocalDate end) {
        return days.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(begin) && !entry.getKey().isAfter(end))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }*/

    public Day getDay(LocalDate index) {
        return days.get(index);
    }

    public String getMonthName() {
        return monthName;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<LocalDate, Day> entry : days.entrySet()) {
            sb.append("Date: ").append(entry.getKey()).append(" -> ").append(entry.getValue().toString()).append("\n");
        }

        return sb.toString();
    }
}