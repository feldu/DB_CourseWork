package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.services.OrderService;
import db.coursework.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/add_order")
    public ResponseEntity<Long> register(@RequestBody OrderDTO orderDto, Authentication authentication) {
        try {
            log.debug("Adding new order. Received data: {}", orderDto);
            Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
            Order order = orderService.saveOrderFromRequest(human, orderDto.getCount(), orderDto.getCaste(), orderDto.getFutureJobs());
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }

    }

    @Data
    @AllArgsConstructor
    private static class OrderDTO {
        private Integer count;
        private String caste;
        private String[] futureJobs;
    }
}
