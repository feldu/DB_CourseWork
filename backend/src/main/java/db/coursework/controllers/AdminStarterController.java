package db.coursework.controllers;

import db.coursework.services.StepsHandlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/admin/start")
public class AdminStarterController {
    private final StepsHandlingService stepsHandlingService;

    @Autowired
    public AdminStarterController(StepsHandlingService stepsHandlingService) {
        this.stepsHandlingService = stepsHandlingService;
    }


    @PostMapping("/first_step")
    public ResponseEntity<String> startFirstStep(@RequestBody Map<String, Long> payload) {
        Long orderId = payload.get("orderId");
        log.debug("Запуск первого этапа для заказа №{}", orderId);
        stepsHandlingService.firstStepHandling(orderId);
        log.debug("Первый этап для заказа №{} завершён", orderId);
        return new ResponseEntity<>("Первый этап успешно завершён", HttpStatus.OK);
    }


    @PostMapping("/second_step")
    public ResponseEntity<String> startSecondStep(@RequestBody Map<String, Long> payload) {
        Long orderId = payload.get("orderId");
        log.debug("Запуск второго этапа для заказа №{}", orderId);
        if (stepsHandlingService.secondStepHandling(orderId))
            return new ResponseEntity<>("Второй этап успешно завершён", HttpStatus.OK);
        else return new ResponseEntity<>("Не удалось завершить выполнение второго этапа", HttpStatus.OK);
    }


    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/third_step")
    public ResponseEntity<String> startThirdStep(@RequestBody Map<String, Long> payload) {
        Long orderId = payload.get("orderId");
        log.debug("Запуск третьего этапа для заказа №{}", orderId);
        stepsHandlingService.thirdStepHandling(orderId);
        return new ResponseEntity<>("Третий этап успешно завершён", HttpStatus.OK);

    }

}
