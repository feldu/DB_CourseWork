package db.coursework.controllers;

import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.services.ManageService;
import db.coursework.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {
    private final ManageService manageService;
    private final UserService userService;

    @Autowired
    public MainController(ManageService manageService, UserService userService) {
        this.manageService = manageService;
        this.userService = userService;
    }

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

    @PostMapping("/add_admin")
    public ResponseEntity<String> addAdmin(@RequestBody Map<String, String> payload) {
        try {
            String username = payload.get("username");
            manageService.addAdmin(username);
            return new ResponseEntity<>("Админ добавлен", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user_info")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        try {
            User user = userService.loadUserByUsername(authentication.getName());
            Role role = new ArrayList<>(user.getHuman().getRoles()).get(0);
            UserDTO userDto = new UserDTO(user.getUsername(), user.getPassword(), user.getHuman().getFullname(), role.getName().split("_")[1]);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((UserDTO) null, HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    @AllArgsConstructor
    private static class UserDTO {
        private String username;
        private String password;
        private String fullname;
        private String role;
    }
}
