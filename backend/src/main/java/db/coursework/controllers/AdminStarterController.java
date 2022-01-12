package db.coursework.controllers;

import db.coursework.entities.Machine;
import db.coursework.entities.Order;
import db.coursework.entities.OvumContainer;
import db.coursework.entities.UseMachineByOvumContainer;
import db.coursework.entities.enums.MachineName;
import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import db.coursework.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/admin/start")
public class AdminStarterController {
    private final OvumContainerService ovumContainerService;
    private final OvumService ovumService;
    private final OrderService orderService;
    private final MachineService machineService;
    private final UseMachineByOvumContainerService useMachineByOvumContainerService;

    @Autowired
    public AdminStarterController(OvumContainerService ovumContainerService, OvumService ovumService, OrderService orderService, MachineService machineService, UseMachineByOvumContainerService useMachineByOvumContainerService) {
        this.ovumContainerService = ovumContainerService;
        this.ovumService = ovumService;
        this.orderService = orderService;
        this.machineService = machineService;
        this.useMachineByOvumContainerService = useMachineByOvumContainerService;
    }

    @Transactional
    @PostMapping("/first_step")
    public ResponseEntity<String> startFirstStep(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            log.debug("Запуск первого этапа для заказа №{}", orderId);
            //Яйцеприемник в бульон со сперматазоидами
            OvumContainer ovumContainer = ovumContainerService.getOrderOvumreceiver(orderId);
            Machine machine = machineService.getMachineByName(MachineName.Бульон_со_сперматозоидами);
            UseMachineByOvumContainer useMachineByOvumContainer = new UseMachineByOvumContainer(new UseMachineByOvumContainerKey(machine.getId(), ovumContainer.getId(), new Date()), machine, ovumContainer, null, null);
            useMachineByOvumContainerService.save(useMachineByOvumContainer);
            log.debug("Яйцеприемник №{} погрузился в резрвуар с бульоном со сперматазоидами №{}", ovumContainer.getId(), machine.getId());
            //После оплодотворения яйцеприёмник вынимают из бульона
            ovumService.updateOvumInOvumContainerByFertilizationTime(ovumContainer.getId(), new Date());
            useMachineByOvumContainer.setEndTime(new Date());
            useMachineByOvumContainerService.save(useMachineByOvumContainer);
            log.debug("Яйцеприемник №{} достали из резрвуара с бульоном со сперматазоидами №{}", ovumContainer.getId(), machine.getId());
            log.debug("Первый этап для заказа №{} завершён", orderId);
            return new ResponseEntity<>("Первый этап успешно завершён", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @PostMapping("/second_step")
    public ResponseEntity<String> startSecondStep(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            log.debug("Запуск второго этапа для заказа №{}", orderId);
            Order order = orderService.findOrderById(orderId).orElse(null);
            if (order == null) throw new RuntimeException("Невозможно найти заказ");
            if (Arrays.asList(new String[]{"Alpha", "Beta"}).contains(order.getCaste().name())) {
                log.debug("Невозможно исполнить заказ №{}. Каста заказа: {}", orderId, order.getCaste());
                throw new RuntimeException("Яйцеклетки для каст Альфа и Бета не дробятся");
            }
            long ovumCount = ovumService.getOvumCountByOrder(order);
            if (ovumCount == order.getHumanNumber()) {
                log.debug("Количество людей совпадает с количеством яйцеклеток и равно {}. Дробление не требуется. Второй этап завершён", ovumCount);
                return new ResponseEntity<>("Второй этап успешно завершён", HttpStatus.OK);
            }
            if (ovumCount > order.getHumanNumber()) {
                log.debug("Количество яйцеклеток: {}. Требуется людей: {} Дробление не требуется", ovumCount, order.getHumanNumber());
                ovumService.removeExtraOvumByOrderId(orderId, ovumCount - order.getHumanNumber());
                log.debug("Удалено {} яйцеклеток. Второй этап завершён.", ovumCount - order.getHumanNumber());
                return new ResponseEntity<>("Второй этап успешно завершён", HttpStatus.OK);
            }
            OvumContainer ovumContainer = ovumContainerService.getOrderOvumreceiver(orderId);
            //Яйцеклетки обрабатывают рентгеновским излучением (multi до x8)
            Integer multi = (int) (Math.random() * (8 - 6) + 6);
            Machine xRay = machineService.getMachineByName(MachineName.Рентген);
            useMachineByOvumContainer(ovumContainer, multi, xRay);

            //Яйцеклетки оставляют на двое суток в interactWithXRay
            multi = null;
            Machine incubator = machineService.getMachineByName(MachineName.Инкубатор);
            useMachineByOvumContainer(ovumContainer, multi, incubator);

            //Яйцеклетки охлаждают (multi до x6)
            multi = (int) (Math.random() * (6 - 4) + 4);
            Machine freezer = machineService.getMachineByName(MachineName.Морозилка);
            useMachineByOvumContainer(ovumContainer, multi, freezer);

            //Яйцеклетки глушат спиртом (multi до x4)
            multi = (int) (Math.random() * (4 - 3) + 3);
            Machine alcohol = machineService.getMachineByName(MachineName.Глушитель_спиртом);
            useMachineByOvumContainer(ovumContainer, multi, alcohol);

            log.debug("Второй этап для заказа №{} завершён", orderId);
            return new ResponseEntity<>("Второй этап успешно завершён", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    private void useMachineByOvumContainer(OvumContainer ovumContainer, Integer multi, Machine machine) {
        UseMachineByOvumContainer interactWithIncubator = new UseMachineByOvumContainer(new UseMachineByOvumContainerKey(machine.getId(), ovumContainer.getId(), new Date()), machine, ovumContainer, null, multi);
        useMachineByOvumContainerService.save(interactWithIncubator);
        log.debug("Яйцеприемник №{} используется {} №{}. Коэффициент: {}", ovumContainer.getId(), machine.getName(), machine.getId(), multi);
        interactWithIncubator.setEndTime(new Date());
        useMachineByOvumContainerService.save(interactWithIncubator);
    }
}
