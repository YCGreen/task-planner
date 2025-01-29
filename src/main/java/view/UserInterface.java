package view;

import model.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class UserInterface {
    @GetMapping("/")
    public String home() {
        return "form";
    }

    @PostMapping("/submit")
    public String submitForm(@RequestParam String name, @RequestParam int length, Model model) {
        Task task = new Task(name, length);
        model.addAttribute("task", task);
        return "submit";
    }

    public static void main(String[] args) {
        SpringApplication.run(UserInterface.class, args);
    }
}
