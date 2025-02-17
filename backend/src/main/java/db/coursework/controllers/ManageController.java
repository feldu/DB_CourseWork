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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/manage")
@RequiredArgsConstructor
public class ManageController {
    private final ManageService manageService;
    private final UserService userService;


    @PostMapping("/new-admin")
    public ResponseEntity<String> addAdmin(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        manageService.addAdmin(username);
        return new ResponseEntity<>("Админ добавлен", HttpStatus.OK);
    }

    @PostMapping("/add_ovum")
    public ResponseEntity<String> addOvum(@RequestBody Map<String, Long> payload) {
        Long ovumCount = payload.get("ovumCount");
        manageService.addFreeOvum(ovumCount);
        log.debug("Было добавлено {} яйцеклеток", ovumCount);
        return new ResponseEntity<>("Яйцеклетки добавлены", HttpStatus.OK);
    }

    @PostMapping("/add_containers")
    public ResponseEntity<String> addOvumContainers(@RequestBody Map<String, String> payload) {
        Long count = Long.valueOf(payload.get("count"));
        String name = payload.get("name");
        manageService.addOvumContainers(count, name);
        return new ResponseEntity<>("Контейнеры добавлены", HttpStatus.OK);
    }

    @PostMapping("/add_material")
    public ResponseEntity<String> addMaterialAndCut(@RequestBody Map<String, String> payload) {
        Long count = Long.valueOf(payload.get("count"));
        manageService.addMaterial(count);
        return new ResponseEntity<>("Материалы добавлены", HttpStatus.OK);
    }
}
