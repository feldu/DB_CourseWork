package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.services.HumanService;
import db.coursework.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HumanService humanService;
    private final OrderService orderService;

    @Autowired
    public AdminController(HumanService humanService, OrderService orderService) {
        this.humanService = humanService;
        this.orderService = orderService;
    }

    @PostMapping("/get_predeterminers")
    public ResponseEntity<List<HumanDTO>> getAllFutureJobTypesNames() {
        try {
            List<Human> predeterminers = humanService.findAllHumanByRoleName("ROLE_PREDETERMINER");
            List<HumanDTO> predeterminersDTO = predeterminers.stream().map(h -> new HumanDTO(h.getId(), h.getFullname())).collect(Collectors.toList());
            return new ResponseEntity<>(predeterminersDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_orders")
    public ResponseEntity<List<OrderDTO>> getAllHumanOrdersByFullname(@RequestBody Map<String, String> payload) {
        try {
            String fullname = payload.get("fullname");
            Human human = humanService.findHumanByFullname(fullname);
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste().name(), order.getFutureJobTypes().stream().map(type -> type.getName().toString()).collect(Collectors.toList()))).collect(Collectors.toList());
            log.debug("Sending {} orders of {}", orderDTOS.size(), fullname);
            return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Data
    @AllArgsConstructor
    private static class HumanDTO {
        Long id;
        String fullname;
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