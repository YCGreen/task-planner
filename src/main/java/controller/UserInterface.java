package view;

import model.Day;
import model.Scheduler;
import model.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Controller
@SpringBootApplication
public class UserInterface {

    private Scheduler scheduler = new Scheduler(3);

    @GetMapping("/")
    public String home() {
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@RequestParam String name, @RequestParam int length, @RequestParam String date, Model model) {
        Task task = new Task(name, length);
        model.addAttribute("task", task);
        scheduler.addTask(task, LocalDate.parse(date));
        HashMap<LocalDate, Day> days = scheduler.getCurrMonth().getDays();
        model.addAttribute("days", days);
        return "submit"; //redirect to calendar
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
