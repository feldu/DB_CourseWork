package db.coursework.controllers;

import db.coursework.dto.OrderDTO;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.services.OrderService;
import db.coursework.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user/orders")
public class UserOrderController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public UserOrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Long> addOrder(@RequestBody OrderDTO orderDto, Authentication authentication) {
        try {
            log.debug("Adding new order. Received data: {}", orderDto);
            Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
            Order order = orderService.saveOrderFromRequest(human, orderDto.getHumanNumber(), orderDto.getCaste(), orderDto.getFutureJobTypes());
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(Authentication authentication) {
        try {
            Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste().name(), order.getFutureJobTypes().stream().map(type -> type.getName().toString()).collect(Collectors.toList()), order.isProcessing())).collect(Collectors.toList());
            log.debug("Sending {} orders", orderDTOS.size());
            return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
