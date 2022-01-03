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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Long> addOrder(@RequestBody OrderDTO orderDto, Authentication authentication) {
        try {
            log.debug("Adding new order. Received data: {}", orderDto);
            Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
            Order order = orderService.saveOrderFromRequest(human, orderDto.getHumanNumber(), orderDto.getCaste(), orderDto.getFutureJobTypes());
            return new ResponseEntity<>(order.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_orders")
    public ResponseEntity<List<OrderDTO>> getAllHumanOrders(Authentication authentication) {
        try {
            Human human = userService.loadUserByUsername(authentication.getName()).getHuman();
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste(), order.getFutureJobTypes().stream().map(type -> type.getName()).collect(Collectors.toList()))).collect(Collectors.toList());
            log.debug("Sending {} orders", orderDTOS.size());
            return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    @AllArgsConstructor
    private static class OrderDTO {
        @Null
        private Long id;
        @NotNull
        private Integer humanNumber;
        @NotNull
        private String caste;
        @NotNull
        private List<String> futureJobTypes;
    }
}
