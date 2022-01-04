package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody UserDTO dto) {
        try {
            Role role = userService.saveRoleByName(dto.getRole());
            Human human = new Human(dto.getFullname(), (Collections.singleton(role)));
            User user = new User(dto.getUsername(), dto.getPassword(), human);
            log.debug("POST request to register user {}", user);
            log.debug("Associated human {}", human);
            log.debug("Human role {}", role);
            boolean isSaved = userService.saveUser(user);
            return isSaved ? new ResponseEntity<>("Пользователь зарегистрирован. Войдите :)", HttpStatus.OK) :
                    new ResponseEntity<>("Пользователь с таким именем уже существует", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error {}", e.getMessage());
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    private static class UserDTO {
        private String username;
        private String password;
        private String fullname;
        private String role;
    }
}
