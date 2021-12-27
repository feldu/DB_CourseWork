package db.coursework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/auth/signin")
    public String signIn() {
        return "forward:/index.html";
    }

    @GetMapping("/auth/signup")
    public String signUp() {
        return "forward:/index.html";
    }
}
