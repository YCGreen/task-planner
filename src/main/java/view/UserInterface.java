package view;

import model.Day;
import model.Scheduler;
import model.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
