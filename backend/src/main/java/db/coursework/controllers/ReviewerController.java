package db.coursework.controllers;

import db.coursework.services.ManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/reviewer")
@RequiredArgsConstructor
public class ReviewerController {
    private final ManageService manageService;

    @PostMapping("/material")
    public ResponseEntity<String> addMaterialWithSize(@RequestBody Map<String, String> payload) {
        int size = Integer.valueOf(payload.get("size"));
        manageService.addMaterial(size);
        return new ResponseEntity<>("Материал добавлен", HttpStatus.OK);
    }
}
