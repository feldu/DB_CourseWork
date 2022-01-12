package db.coursework.controllers;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.Ovum;
import db.coursework.services.HumanService;
import db.coursework.services.OrderService;
import db.coursework.services.OvumService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HumanService humanService;
    private final OrderService orderService;
    private final OvumService ovumService;

    @Autowired
    public AdminController(HumanService humanService, OrderService orderService, OvumService ovumService) {
        this.humanService = humanService;
        this.orderService = orderService;
        this.ovumService = ovumService;
    }

    @PostMapping("/get_predeterminers")
    public ResponseEntity<List<HumanDTO>> getAllFutureJobTypesNames() {
        try {
            List<Human> predeterminers = humanService.findAllHumanByRoleName("ROLE_PREDETERMINER");
            List<HumanDTO> predeterminersDTO = predeterminers.stream().map(h -> new HumanDTO(h.getId(), h.getFullname())).collect(Collectors.toList());
            log.debug("Sending {} predeterminers", predeterminersDTO.size());
            return new ResponseEntity<>(predeterminersDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_orders")
    public ResponseEntity<List<OrderDTO>> getAllHumanOrdersByFullname(@RequestBody Map<String, String> payload) {
        try {
            String fullname = payload.get("fullname");
            Human human = humanService.findHumanByFullname(fullname);
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste().name(), order.getFutureJobTypes().stream().map(type -> type.getName().toString()).collect(Collectors.toList()), order.isProcessing())).collect(Collectors.toList());
            log.debug("Sending {} orders of {}", orderDTOS.size(), fullname);
            return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_free_ovum_count")
    public ResponseEntity<Long> getFreeOvumCount() {
        try {
            Long count = ovumService.getOvumCountByOrder(null);
            log.debug("Sending {} free ovum", count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove_extra_ovum_by_order")
    public ResponseEntity<String> removeExtraOvumByOrder(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            Order order = orderService.findOrderById(orderId).orElse(null);
            if (order == null)
                throw new RuntimeException("Заказа не существует");
            //Уничтожаем "лишние"
            Long ovumCount = ovumService.getOvumCountByOrder(order);
            if (ovumCount > order.getHumanNumber()) {
                log.debug("Количество яйцеклеток: {}. Требуется людей: {}", ovumCount, order.getHumanNumber());
                ovumService.removeExtraOvumByOrderId(orderId, ovumCount - order.getHumanNumber());
                log.debug("Удалено {} яйцеклеток.", ovumCount - order.getHumanNumber());
            }
            return new ResponseEntity<>("Удаление прошло успешно", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bind_free_ovum")
    public ResponseEntity<String> bindFreeOvum(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            Long count = payload.get("count");
            log.debug("Bind {} ovum to order №{}", count, orderId);
            ovumService.bindFreeOvum(orderId, count);
            return new ResponseEntity<>("Началось выполнение заказа №" + orderId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_ovum_by_order")
    public ResponseEntity<List<Ovum>> getOvumByOrder(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            List<Ovum> ovumList = ovumService.findAllOvumByOrder_Id(orderId);
            log.debug("Sending {} ovum of {} order", ovumList.size(), orderId);
            return new ResponseEntity<>(ovumList, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_ovum")
    public ResponseEntity<String> updateOvum(@RequestBody OvumDTO ovumDTO) {
        try {
            log.debug("Обновляем яйцеклетку с id {}", ovumDTO.id);
            ovumService.updateOvumByOvumDTOFields(ovumDTO.getId(), ovumDTO.isBud(), ovumDTO.getFertilizationTime(), ovumDTO.getEmbryoTime(), ovumDTO.getBabyTime());
            return new ResponseEntity<>("Яйцеклетка успешно обновлена", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось обновить яйцеклетку", HttpStatus.BAD_REQUEST);
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
    private static class OvumDTO {
        @NotNull
        Long id;
        @NotNull
        private boolean isBud;
        private Date fertilizationTime;
        private Date embryoTime;
        private Date babyTime;
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
        @NotNull
        private boolean isProcessing;
    }
}
