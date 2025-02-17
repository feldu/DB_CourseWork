package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.services.ManageService;
import db.coursework.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/volunteer/")
@RequiredArgsConstructor
public class VolunteerController {
    private final ManageService manageService;
    private final UserService userService;

    @PostMapping("/ovum")
    public ResponseEntity<String> addOvum(Authentication authentication) {
        Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
        try {
            manageService.addFreeOvum(human);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Пошёл нафик", HttpStatus.NO_CONTENT);
        }
        log.debug("Было добавлена яйцеклетка  добровольца {}", human.getFullname());
        return new ResponseEntity<>("Яйцеклетка добавлена", HttpStatus.OK);
    }
}