package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.entities.HumanRole;
import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.services.RoleService;
import db.coursework.services.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody @Valid User user,
                                        BindingResult bindingResult) {
        try {
            log.debug("POST request to login user {}", user.getUsername());
            if (bindingResult.hasErrors()) {
                log.error("Validation error");
                return new ResponseEntity<>("Ошибка валидации", HttpStatus.BAD_REQUEST);
            }
            String token = userService.getUserToken(user);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error("Invalid user credentials {}", e.getMessage());
            return new ResponseEntity<>("Неверные учетные данные пользователя", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Unexpected error {}", e.getMessage());
            return new ResponseEntity<>("Непредвиденная ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody DTO dto) {
        try {
            User user = new User(dto.getUsername(), dto.getPassword());
            Role role = roleService.saveRole(dto.getRole());
            Human human = new Human();
            human.setFullname(dto.getFullname());
            human.setHumanRoles(Collections.singleton(new HumanRole(human, role)));
            role.setHumanRoles(Collections.singleton(new HumanRole(human, role)));
            user.setHuman(human);
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
    private static class DTO {
        private String username;
        private String password;
        private String fullname;
        private String role;
    }
}
