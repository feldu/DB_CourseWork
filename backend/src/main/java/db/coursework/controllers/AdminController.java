package db.coursework.controllers;

import db.coursework.dto.*;
import db.coursework.entities.*;
import db.coursework.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/users")
    public ResponseEntity<List<HumanDTO>> getUsersByRoleName(@RequestParam String roleName) {
        try {
            List<Human> predeterminers = humanService.findAllHumanByRoleName(roleName);
            List<HumanDTO> predeterminersDTO = predeterminers.stream().map(h -> new HumanDTO(h.getId(), h.getFullname())).collect(Collectors.toList());
            log.debug("Sending {} predeterminers", predeterminersDTO.size());
            return new ResponseEntity<>(predeterminersDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orders/human/{id}")
    public ResponseEntity<List<OrderDTO>> getAllHumanOrdersById(@PathVariable Long id) {
        try {
            Human human = humanService.findHumanById(id);
            List<Order> orders = orderService.findAllOrdersByHuman(human);
            List<OrderDTO> orderDTOS = orders.stream().map(order -> new OrderDTO(order.getId(), order.getHumanNumber(), order.getCaste().name(), order.getFutureJobTypes().stream().map(type -> type.getName().toString()).collect(Collectors.toList()), order.isProcessing())).collect(Collectors.toList());
            log.debug("Sending {} orders of {}", orderDTOS.size(), human.getFullname());
            return new ResponseEntity<>(orderDTOS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ovums/count/order/{orderId}")
    public ResponseEntity<Long> getOvumCountByOrder(@PathVariable String orderId) {
        try {
            Long id;
            if (orderId.equals("null")) id = null;
            else id = Long.valueOf(orderId);
            Long count = ovumService.getOvumCountByOrderId(id);
            log.debug("Sending {} free ovum", count);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/ovums/order/{orderId}")
    public ResponseEntity<String> removeExtraOvumByOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.findOrderById(orderId);
            if (order == null)
                throw new RuntimeException("Заказа не существует");
            //Уничтожаем "лишние"
            Long ovumCount = ovumService.getOvumCountByOrderId(order.getId());
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

    @PostMapping("/ovums/bind/{count}/order/{orderId}")
    public ResponseEntity<String> bindFreeOvum(@PathVariable Long orderId, @PathVariable Long count) {
        try {
            log.debug("Bind {} ovum to order №{}", count, orderId);
            ovumService.bindFreeOvum(orderId, count);
            return new ResponseEntity<>("Началось выполнение заказа №" + orderId, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/ovums/{id}")
    public ResponseEntity<String> updateOvum(@RequestBody OvumDTO ovumDTO) {
        try {
            log.debug("Обновляем яйцеклетку с id {}", ovumDTO.getId());
            ovumService.updateOvumByOvumDTOFields(ovumDTO.getId(), ovumDTO.isBud(), ovumDTO.getFertilizationTime(), ovumDTO.getEmbryoTime(), ovumDTO.getBabyTime());
            return new ResponseEntity<>("Яйцеклетка успешно обновлена", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось обновить яйцеклетку", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/use-machine-by-ovum-container/order/{orderId}")
    public ResponseEntity<List<UseMachineByOvumContainerDTO>> getUseMachine(@PathVariable Long orderId) {
        try {
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

    @GetMapping("/move-ovum-container-to-room/order/{orderId}")
    public ResponseEntity<List<MoveOvumContainerToRoomDTO>> getMoveContainer(@PathVariable Long orderId) {
        try {
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

    @GetMapping("/add-material-to-ovum-container/order/{orderId}")
    public ResponseEntity<List<AddMaterialToOvumContainerDTO>> getAddMaterial(@PathVariable Long orderId) {
        try {
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

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<String> removeOrder(@PathVariable Long orderId) {
        try {
            orderService.removeOrderInfo(orderId);
            return new ResponseEntity<>("Сведения о заказе удалены", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Не удалось удалить заказ))))", HttpStatus.BAD_REQUEST);
        }
    }
}
