package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scheduler {

    private ArrayList<Month> months;

    public Scheduler(int availHours) {
        months = new ArrayList<>();
        LocalDate currMonth = LocalDate.now().withDayOfYear(1);

        for (int i = 0; i < 12; i++) {
            months.add(new Month(availHours, currMonth));
            currMonth = currMonth.plusMonths(1);
        }
    }

    public String getMonthName(int month) {
        return months.get(month - 1).getMonthName();
    }

    public int getFirstDay(int month) {
        return months.get(month - 1).getFirstDay();
    }

    public void setMonthAvailHours(double availHours, LocalDate month) {
        months.get(month.getMonthValue() - 1).setAvailHours(availHours);
    }

    public void setRangeAvailHours(double availHours, LocalDate begin, LocalDate end) {
        List<Day> days = getDaysInRange(begin, end);
        days.forEach(day -> day.setAvailHours(availHours));
    }

    public Month getCurrMonth() {
        return months.get(LocalDate.now().getMonthValue() - 1);
    }

    public Month getMonth(int month) {
        return months.get(month - 1);
    }

    public List<Month> getMonths() {
        return months;
    }

    private List<Day> getAvailDays(LocalDate dueDate) {
        LocalDate now = LocalDate.now();

        int monthStart = now.getMonthValue() - 1;
        int monthDue = dueDate.getMonthValue() - 1;

        List<Day> days = new ArrayList<>();

        if(monthDue - monthStart < 0) {
            System.out.println("Invalid: due date before start date");
            return null;
        }  else if(monthStart == monthDue) {
            days = getDaysInRange(now, dueDate); //TODO: fix for multiple years in months then delete redundant line
        } else if(dueDate.isAfter(now.withDayOfYear(LocalDate.now().lengthOfYear()))) {
            addYear();
        } else {
            days.addAll(getDaysInRange(now, dueDate));
        }

        return days;
    }

    public boolean addTask(Task task, LocalDate dueDate) {
        List<Day> days = getAvailDays(dueDate);

        if(days == null) {
            System.out.println("days is empty");
            return false; //TODO: add popup message
        }

        return task.getMode() ? addTaskIntermittent(task, days) : addTaskReg(task, days);
    }

    private boolean addTaskIntermittent(Task task, List<Day> days) {
        Day today;
        double timePerDay = calculateTimePerDay(days, task.getLen());
        double timeAllotted = 0;
        int currDay = 0;

        while (timeAllotted < task.getLen() && currDay < days.size()) {

            today = days.get(currDay);
            double availHours = today.getAvailHours();

            if (availHours >= timePerDay) {
                today.addPartialTask(task, timePerDay);
                timeAllotted += timePerDay;
            } else if (availHours < timePerDay) {
                today.addPartialTask(task, availHours);
                timeAllotted += availHours;
            }

            currDay++;
        }

        return timeAllotted >= task.getLen();
    }

    private boolean addTaskReg(Task task, List<Day> days) {
        Day today;
        double len;
        double timeAllotted = 0;
        int currDay = 0;

        while(!task.isComplete() && currDay < days.size()) {
            today = days.get(currDay);
            len = task.getLen();
            double availHours = today.getAvailHours();

            if(availHours >= len) {
                today.addTask(task);
                timeAllotted += len;
                task.complete();
            }

            else if(availHours > 0){
                today.addPartialTask(task, availHours);
                timeAllotted += availHours;
                task.setLenComplete(timeAllotted);
            }

            currDay++;
        }

        return task.isComplete();
    }

    private double calculateTimePerDay(List<Day> days, double len) {
        double availTime = 0;
        for (Day day : days) {
            availTime += day.getAvailHours();
        }

        double timePerDay = len / availTime * 3; //TODO: replace with availHours
        //months.get(LocalDate.now().getMonthValue()).getDay(LocalDate.now()).getAvailHours();
        if (timePerDay < 0) {
            return 0; //popup: not enough time
        }

        return timePerDay >= .25 ? timePerDay : .25;

    }

    public void addYear() {
        LocalDate currMonth = LocalDate.now().withDayOfYear(1).plusYears(1);

        for (int i = 0; i < 12; i++) {
            currMonth = currMonth.plusMonths(1);
            months.add(new Month(3, currMonth)); //TODO: replace availHours
        }
    }

    public List<Day> getDaysInRange(LocalDate start, LocalDate end) {
        return IntStream.rangeClosed(start.getMonthValue() - 1, end.getMonthValue() - 1)
                .mapToObj(i -> months.get(i))
                .flatMap(month -> month.getDays().entrySet().stream())
                .filter(entry -> !entry.getKey().isBefore(start) && !entry.getKey().isAfter(end))
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

}