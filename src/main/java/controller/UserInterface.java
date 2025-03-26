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
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@SpringBootApplication
public class UserInterface {

    private Scheduler scheduler = new Scheduler(3);
    LocalDate currentDate = LocalDate.now();

    @GetMapping("/")
    public String home() {
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@RequestParam(required = false) String direction, @ModelAttribute TaskFormData taskFormData, Model model) {
        if (direction != null) {
            if ("previous".equals(direction)) {
                currentDate = currentDate.minusDays(1);
            } else if ("next".equals(direction)) {
                currentDate = currentDate.plusDays(1);
            }
        }

        if (taskFormData.getName() != null && taskFormData.getLength() != null && taskFormData.getDate() != null) {
            scheduler.addTask(new Task(taskFormData.getName(), taskFormData.getLength()), LocalDate.parse(taskFormData.getDate()));
        }

        HashMap<LocalDate, Day> days = scheduler.getCurrMonth().getDays();
        Day currentDay = days.get(currentDate);
        model.addAttribute("tasks", currentDay != null ? currentDay.getTasks() : new ArrayList<>());
        model.addAttribute("currentDate", currentDate);
        return "submit";
    }

    @PostMapping("/updateHours")
    public String updateHours(@ModelAttribute AvailHoursFormData hoursFormData, Model model) {
        if (hoursFormData.getRangeStart() != null && hoursFormData.getRangeEnd() != null && hoursFormData.getHours() != null) {
            LocalDate start = LocalDate.parse(hoursFormData.getRangeStart());
            LocalDate end = LocalDate.parse(hoursFormData.getRangeEnd());
            scheduler.setRangeAvailHours(hoursFormData.getHours(), start, end);
        }

        // Add updated scheduler data to the model if needed
        //model.addAttribute("currentDate", currentDate);
        return "submit"; // Render the update hours page
    }

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        HashMap<LocalDate, Day> days = scheduler.getCurrMonth().getDays();
        model.addAttribute("days", days);
        return "calendar";
    }


    public static void main(String[] args) {
        SpringApplication.run(UserInterface.class, args);
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
