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

    public void setAvailHours(int availHours) {
        days.values().forEach(day -> day.setAvailHours(availHours));
    }


    public List<Day> getDaysInRange(LocalDate begin, LocalDate end) {
        return days.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(begin) && !entry.getKey().isAfter(end))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Day getDay(LocalDate index) {
        return days.get(index);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<LocalDate, Day> entry : days.entrySet()) {
            sb.append("Date: ").append(entry.getKey()).append(" -> ").append(entry.getValue().toString()).append("\n");
        }

        return sb.toString();
    }
}