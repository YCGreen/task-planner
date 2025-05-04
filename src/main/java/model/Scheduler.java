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
        } else if(dueDate.isAfter(now.withDayOfYear(LocalDate.now().lengthOfYear()))) {
            addYear();
        } else if(monthStart == monthDue) {
            days = getDaysInRange(now, dueDate); //TODO: fix for multiple years in months then delete redundant line
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

        int currDay = 0;

        if(!task.getMode()) { //regular, not intermittent

            double timeAllotted = 0;
            double len = task.getLen();

            while(!task.isComplete()) {
                if(currDay >= days.size()) {
                    return false; //add popup: not enough available time
                }

                Day today = days.get(currDay);
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
        }

        //else: is intermittent
            else {


            double availTime = 0;
            for (Day day : days) {
                availTime += day.getAvailHours();
            }

            double timePerDay = task.getLen() / availTime * 3; //TODO: replace with availHours
            //months.get(LocalDate.now().getMonthValue()).getDay(LocalDate.now()).getAvailHours();
            if (timePerDay < 0) {
                return false; //popup: not enough time
            }

            timePerDay = timePerDay >= .25 ? timePerDay : .25;

            double timeAllotted = 0;

            while (timeAllotted < task.getLen() && currDay < days.size()) {
                double timeLeftToday = days.get(currDay).getAvailHours();
                if (timeLeftToday >= timePerDay) {
                    days.get(currDay).addPartialTask(task, timePerDay);
                    timeAllotted += timePerDay;
                } else if (timeLeftToday < timePerDay) {
                    days.get(currDay).addPartialTask(task, timeLeftToday);
                    timeAllotted += timeLeftToday;
                }
                currDay++;
            }
        }
        return true;
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
//when you do an intermittent then an all at once it doesn't do the all at once option