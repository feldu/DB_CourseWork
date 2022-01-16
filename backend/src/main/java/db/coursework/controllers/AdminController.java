package db.coursework.controllers;

import db.coursework.entities.*;
import db.coursework.services.*;
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
import java.util.ArrayList;
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
    private final OvumContainerService ovumContainerService;
    private final UseMachineByOvumContainerService useMachineByOvumContainerService;
    private final MoveOvumContainerToRoomService moveOvumContainerToRoomService;
    private final AddMaterialToOvumContainerService addMaterialToOvumContainerService;

    @Autowired
    public AdminController(HumanService humanService, OrderService orderService, OvumService ovumService, OvumContainerService ovumContainerService, UseMachineByOvumContainerService useMachineByOvumContainerService, MoveOvumContainerToRoomService moveOvumContainerToRoomService, AddMaterialToOvumContainerService addMaterialToOvumContainerService) {
        this.humanService = humanService;
        this.orderService = orderService;
        this.ovumService = ovumService;
        this.ovumContainerService = ovumContainerService;
        this.useMachineByOvumContainerService = useMachineByOvumContainerService;
        this.moveOvumContainerToRoomService = moveOvumContainerToRoomService;
        this.addMaterialToOvumContainerService = addMaterialToOvumContainerService;
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
    public ResponseEntity<List<OrderDTO>> getAllHumanOrdersByFullname(@RequestBody Map<String, Long> payload) {
        try {
            Long id = payload.get("id");
            Human human = humanService.findHumanById(id);
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste().name(), order.getFutureJobTypes().stream().map(type -> type.getName().toString()).collect(Collectors.toList()), order.isProcessing())).collect(Collectors.toList());
            log.debug("Sending {} orders of {}", orderDTOS.size(), human.getFullname());
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
            Order order = orderService.findOrderById(orderId);
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

    @PostMapping("/get_use_machine")
    public ResponseEntity<List<UseMachineByOvumContainerDTO>> getUseMachine(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            log.debug("Получаем журнал использования машин контейнерами заказа {}", orderId);
            Order order = orderService.findOrderById(orderId);
            if (order == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            List<OvumContainer> ovumContainers = ovumContainerService.getAllOrderOvumContainers(orderId);
            List<UseMachineByOvumContainer> useMachineByOvumContainerList = new ArrayList<>(ovumContainers.size());
            for (OvumContainer ovumContainer : ovumContainers) {
                List<UseMachineByOvumContainer> useMachineForCurrentOvumContainer = useMachineByOvumContainerService.findAllByOvumContainer_Id(ovumContainer.getId());
                useMachineByOvumContainerList.addAll(useMachineForCurrentOvumContainer);
            }
            List<UseMachineByOvumContainerDTO> useDTO = useMachineByOvumContainerList.stream().map(entry -> new UseMachineByOvumContainerDTO(entry.getMachine(), entry.getOvumContainer(), entry.getId().getStartTime(), entry.getEndTime(), entry.getTotalBudsCount())).collect(Collectors.toList());
            log.debug("Извлечено {} записей для {} контейнеров", useMachineByOvumContainerList.size(), ovumContainers.size());
            return new ResponseEntity<>(useDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_move_container")
    public ResponseEntity<List<MoveOvumContainerToRoomDTO>> getMoveContainer(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            log.debug("Получаем журнал передвижения контейнеров заказа {}", orderId);
            Order order = orderService.findOrderById(orderId);
            if (order == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            List<OvumContainer> ovumContainers = ovumContainerService.getAllOrderOvumContainers(orderId);
            List<MoveOvumContainerToRoom> moveOvumContainerToRoomList = new ArrayList<>(ovumContainers.size());
            for (OvumContainer ovumContainer : ovumContainers) {
                List<MoveOvumContainerToRoom> moveForCurrentOvumContainer = moveOvumContainerToRoomService.findAllByOvumContainer_Id(ovumContainer.getId());
                moveOvumContainerToRoomList.addAll(moveForCurrentOvumContainer);
            }
            List<MoveOvumContainerToRoomDTO> moveDTO = moveOvumContainerToRoomList.stream().map(entry -> new MoveOvumContainerToRoomDTO(entry.getOvumContainer(), entry.getRoom(), entry.getId().getArrivalTime())).collect(Collectors.toList());
            log.debug("Извлечено {} записей для {} контейнеров", moveOvumContainerToRoomList.size(), ovumContainers.size());
            return new ResponseEntity<>(moveDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/get_add_material")
    public ResponseEntity<List<AddMaterialToOvumContainerDTO>> getAddMaterial(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            log.debug("Получаем журнал добавления материалов в контейнеры заказа {}", orderId);
            Order order = orderService.findOrderById(orderId);
            if (order == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            List<OvumContainer> ovumContainers = ovumContainerService.getAllOrderOvumContainers(orderId);
            List<AddMaterialToOvumContainer> addMaterialToOvumContainerList = new ArrayList<>(ovumContainers.size());
            for (OvumContainer ovumContainer : ovumContainers) {
                List<AddMaterialToOvumContainer> addMaterialToOvumContainers = addMaterialToOvumContainerService.findAllByOvumContainer_Id(ovumContainer.getId());
                addMaterialToOvumContainerList.addAll(addMaterialToOvumContainers);
            }
            List<AddMaterialToOvumContainerDTO> addDTO = addMaterialToOvumContainerList.stream().map(entry -> new AddMaterialToOvumContainerDTO(entry.getMaterial(), entry.getOvumContainer(), entry.getInsertionTime())).collect(Collectors.toList());
            log.debug("Извлечено {} записей для {} контейнеров", addMaterialToOvumContainerList.size(), ovumContainers.size());
            return new ResponseEntity<>(addDTO, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/remove_order_info")
    public ResponseEntity<String> removeOrder(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            orderService.removeOrderInfo(orderId);
            return new ResponseEntity<>("Сведения о заказе удалены", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось удалить заказ))))", HttpStatus.BAD_REQUEST);
        }
    }


    @Data
    @AllArgsConstructor
    private static class UseMachineByOvumContainerDTO {
        @NotNull
        Machine machine;
        @NotNull
        OvumContainer ovumContainer;
        @NotNull
        Date startTime;
        @NotNull
        Date endTime;
        Integer totalBudsCount;
    }

    @Data
    @AllArgsConstructor
    private static class MoveOvumContainerToRoomDTO {
        @NotNull
        OvumContainer ovumContainer;
        @NotNull
        Room room;
        @NotNull
        Date arrivalTime;
    }

    @Data
    @AllArgsConstructor
    private static class AddMaterialToOvumContainerDTO {
        @NotNull
        Material material;
        @NotNull
        OvumContainer ovumContainer;
        @NotNull
        Date insertionTime;
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
