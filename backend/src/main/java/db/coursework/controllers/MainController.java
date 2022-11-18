package db.coursework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/auth/*")
    public String auth() {
        return "/index.html";
    }

    @GetMapping("/user")
    public String user() {
        return "/index.html";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/index.html";
    }
}
