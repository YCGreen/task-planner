package controller;

import model.Day;
import model.Scheduler;
import model.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.TreeMap;

@Controller
@SpringBootApplication
public class UserInterface {

    private Scheduler scheduler = new Scheduler(3);
    int currMonth = LocalDate.now().getMonthValue();

    @GetMapping("/")
    public String home() {
        return "form";
    }

    @GetMapping("/submit")
    public String submitForm(@RequestParam(required = false) String direction, @ModelAttribute TaskFormData taskFormData, Model model) {
        if (direction != null) {
            if(currMonth == 12) {
                scheduler.addYear();
                currMonth = 0;
            }
            if ("previous".equals(direction)) {
                currMonth--;
            } else if ("next".equals(direction)) {
                currMonth++;
            }
        }

        int offsetDays = scheduler.getFirstDay(currMonth);
        offsetDays = offsetDays == 7 ? 1 : offsetDays + 1;
        model.addAttribute("offsetDays", offsetDays);

        model.addAttribute("monthName", scheduler.getMonthName(currMonth));

        if (taskFormData.isValid()) {
            Task newTask = new Task(
                    taskFormData.getName(),
                    taskFormData.getLength(),
                    taskFormData.isWorkStyle()
            );

            boolean taskAdded = scheduler.addTask(newTask, LocalDate.parse(taskFormData.getDate()));
            if (!taskAdded) {
                model.addAttribute("showPopup", true);
            }
        }


        TreeMap<LocalDate, Day> days = scheduler.getMonth(currMonth).getDays();
        model.addAttribute("days", days);

        return "submit";
    }

    @PostMapping("/updateHours") //TODO: work on me
    public String updateHours(@ModelAttribute AvailHoursFormData hoursFormData, Model model) {
        if (hoursFormData.getRangeStart() != null && hoursFormData.getRangeEnd() != null && hoursFormData.getHours() != null) {
            LocalDate start = LocalDate.parse(hoursFormData.getRangeStart());
            LocalDate end = LocalDate.parse(hoursFormData.getRangeEnd());
            scheduler.setRangeAvailHours(hoursFormData.getHours(), start, end);
            currMonth = 0;
        }

        return "submit";
    }

    public static void main(String[] args) {
        SpringApplication.run(UserInterface.class, args);
    }



}


/*

On home screen: create account. username, password
Set up preferences: available hours per day this month/week/range
Rather work on projects intermittently or all at once? need to create functionality backend for this
Options: View calendar, set availability, add new task (future: delete existing task)
View calendar: displays all tasks on a week-by-week or by month
Set availability: set how many hours per day to work on all tasks (by week basis) Future: set actual hours eg 9-10 PM
maybe just set standard hours per day you have available and then if there are any abnormal days set those
Add new task: task name, amount of time to complete, deadline. option: split up in all available days
or do in shorter number of days with more work per day? maybe set that in set availability
Enter. then task scheduler schedules and leads to view calendar


TODO:
fix so you don't get error when hit currMonth = -1
 */
