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

        if(monthStart - monthDue < 0) {
            System.out.println("Invalid: due date before start date");
            return null;
        } else if(dueDate.isAfter(now.withDayOfYear(LocalDate.now().lengthOfYear()))) {
            addYear();
        } else if(monthStart == monthDue) {
            days = months.get(monthStart).getDaysInRange(now, dueDate); //TODO: fix for multiple years in months then delete redundant line
        } else {
            days.addAll(months.get(monthStart).getDaysInRange(now, dueDate));
        }

        return days;
    }


    public boolean addTask(Task task, LocalDate dueDate) {
        if(!task.getMode()) {
            List<Day> days = getAvailDays(dueDate);
            int daysSize = days.size();

            int currDay = 0;

            AssignStatus status = addTask(days.get(currDay), task);

            while(status != AssignStatus.COMPLETE) {
                if(++currDay >= days.size()) {
                    return false;
                }
                status = addTask(days.get(currDay), task);
            }

            return true;
        }

        List<Day> days = getAvailDays(dueDate);
        int daysSize = days.size();

        int currDay = 0;

        AssignStatus status = addTask1(days.get(currDay), task, daysSize);

        while(status != AssignStatus.COMPLETE) {
            if(++currDay >= days.size()) {
                return false;
            }
            status = addTask1(days.get(currDay), task, daysSize);
        }

        return true;


    }
    private AssignStatus addTask1(Day day, Task task, int days) {
        double availHours = day.getAvailHours();
        double taskLen = task.getLen();
        double timePerDay = taskLen;

        if(task.getMode()) {
            double periods = taskLen / days;
            timePerDay = periods >= 15 ? periods : 15;
        }

        if(availHours >= timePerDay) {
            day.addTask(task);
            task.setLenComplete(timePerDay);
            day.subtractHours(timePerDay);
            task.begin();
            return task.getStatus();
        }

        else if(availHours > 0 && availHours < timePerDay) {
            addPartialTask(day, task, availHours);
            return AssignStatus.BEGUN;
        }

        return AssignStatus.UNBEGUN;
    }

    private AssignStatus addTask(Day day, Task task) {
        double availHours = day.getAvailHours();
        double taskLen = task.getLen();

        if(availHours >= taskLen) {
            addCompleteTask(day, task, taskLen);
            return AssignStatus.COMPLETE;
        }

        else if(availHours > 0 && availHours < taskLen) {
            addPartialTask(day, task, taskLen);
            return AssignStatus.BEGUN;
        }

        return AssignStatus.UNBEGUN;
    }

    private void addCompleteTask(Day day, Task task, double time) {
        day.addTask(task);
        day.subtractHours(time);
     //   task.setLenComplete(0); what is this??
        task.complete();
    }

    private void addPartialTask(Day day, Task task, double time) {
        day.addTask(task);
        task.setLenComplete(time);
        day.subtractHours(time);
        task.begin();
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
