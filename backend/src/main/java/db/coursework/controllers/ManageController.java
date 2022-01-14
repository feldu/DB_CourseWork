package db.coursework.controllers;

import db.coursework.services.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/manage")
public class ManageController {
    private final ManageService manageService;

    @Autowired
    public ManageController(ManageService manageService) {
        this.manageService = manageService;
    }

    @PostMapping("/add_ovum")
    public ResponseEntity<String> addOvum(@RequestBody Map<String, Long> payload) {
        try {
            Long ovumCount = payload.get("ovumCount");
            manageService.addFreeOvum(ovumCount);
            log.debug("Было добавлено {} яйцеклеток", ovumCount);
            return new ResponseEntity<>("Яйцеклетки добавлены", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/add_containers")
    public ResponseEntity<String> addOvumContainers(@RequestBody Map<String, String> payload) {
        try {
            Long count = Long.valueOf(payload.get("count"));
            String name = payload.get("name");
            manageService.addOvumContainers(count, name);
            return new ResponseEntity<>("Контейнеры добавлены", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add_material")
    public ResponseEntity<String> addMaterialAndCut(@RequestBody Map<String, String> payload) {
        try {
            Long count = Long.valueOf(payload.get("count"));
            String name = payload.get("name");
            manageService.addMaterialAndCut(count, name);
            return new ResponseEntity<>("Материалы добавлены", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
