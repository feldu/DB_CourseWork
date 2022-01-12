package db.coursework.controllers;

import db.coursework.entities.*;
import db.coursework.entities.enums.MachineName;
import db.coursework.entities.keys.AddMaterialToOvumContainerKey;
import db.coursework.entities.keys.MoveOvumContainerToRoomKey;
import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import db.coursework.services.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Slf4j
@Controller
@RequestMapping("/admin/start")
public class AdminStarterController {
    private final OvumContainerService ovumContainerService;
    private final OvumService ovumService;
    private final OrderService orderService;
    private final MachineService machineService;
    private final MaterialService materialService;
    private final RoomService roomService;
    private final LabelService labelService;
    private final UseMachineByOvumContainerService useMachineByOvumContainerService;
    private final MoveOvumContainerToRoomService moveOvumContainerToRoomService;
    private final AddMaterialToOvumContainerService addMaterialToOvumContainerService;


    @Autowired
    public AdminStarterController(OvumContainerService ovumContainerService, OvumService ovumService, OrderService orderService, MachineService machineService, MaterialService materialService, RoomService roomService, LabelService labelService, UseMachineByOvumContainerService useMachineByOvumContainerService, MoveOvumContainerToRoomService moveOvumContainerToRoomService, AddMaterialToOvumContainerService addMaterialToOvumContainerService) {
        this.ovumContainerService = ovumContainerService;
        this.ovumService = ovumService;
        this.orderService = orderService;
        this.machineService = machineService;
        this.materialService = materialService;
        this.roomService = roomService;
        this.labelService = labelService;
        this.useMachineByOvumContainerService = useMachineByOvumContainerService;
        this.moveOvumContainerToRoomService = moveOvumContainerToRoomService;
        this.addMaterialToOvumContainerService = addMaterialToOvumContainerService;
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

    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/second_step")
    public ResponseEntity<String> startSecondStep(@RequestBody Map<String, Long> payload) {
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
    }


    private void useMachineByOvumContainer(OvumContainer ovumContainer, Integer multi, Machine machine) {
        UseMachineByOvumContainer interactWithIncubator = new UseMachineByOvumContainer(new UseMachineByOvumContainerKey(machine.getId(), ovumContainer.getId(), new Date()), machine, ovumContainer, null, multi);
        useMachineByOvumContainerService.save(interactWithIncubator);
        log.debug("Яйцеприемник №{} используется {} №{}. Коэффициент: {}", ovumContainer.getId(), machine.getName(), machine.getId(), multi);
        interactWithIncubator.setEndTime(new Date());
        useMachineByOvumContainerService.save(interactWithIncubator);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @PostMapping("/third_step")
    public ResponseEntity<String> startThirdStep(@RequestBody Map<String, Long> payload) {
        Long orderId = payload.get("orderId");
        log.debug("Запуск третьего этапа для заказа №{}", orderId);
        Order order = orderService.findOrderById(orderId).orElse(null);
        if (order == null) throw new RuntimeException("Невозможно найти заказ");
        List<OvumContainer> bottles = ovumContainerService.getFreeBottles(order.getHumanNumber());
        List<Ovum> ovumList = ovumService.findAllByOrder_Id(orderId);
        if (bottles.size() < ovumList.size()) {
            log.debug("Недостаточно бутылок. Бутылок: {}; яйцеклеток: {}.", bottles.size(), ovumList.size());
            throw new RuntimeException("Недостаточно бутылок для размещения всех яйцеклеток. Свободно бутылок: " + bottles.size() + ".");
        }
        //Яйцеклетки помещают в Укупорочный зал в бутыли
        Room room = roomService.findRoomByName("Укупорочный зал");
        if (room == null)
            throw new RuntimeException("Укупорочный зал не найден...");
        for (OvumContainer bottle : bottles) {
            MoveOvumContainerToRoom moveOvumContainerToRoom = new MoveOvumContainerToRoom(new MoveOvumContainerToRoomKey(bottle.getId(), room.getId(), new Date()), bottle, room);
            moveOvumContainerToRoomService.save(moveOvumContainerToRoom);
        }
        IntStream.range(0, ovumList.size()).forEachOrdered(i -> ovumList.get(i).setOvumContainer(bottles.get(i)));
        log.debug("Яйцеклетки помещены в бутыли");

            /*
            13.	* В Органохранилище поставляются свежие свиные туши
                a.	* Если свиная туша полностью некачественная, то её утилизируют
                b.	* Если свиная туша некачественная частично, то из качественных частей вырезают лоскуты определённого размера, а оставшиеся утилизируют
                i.	* Если качественной части свиной туши не хватает на лоскуты определённого размера, её утилизируют
                c.	* Если свиная туша полностью качественная, то её полностью нарезают на лоскуты определённого размера
            14.	* Лоскуты доставляются в Укупорочный зал
                */

        //В бутыли помещают лоскут свежей свиной брюшины
        for (OvumContainer bottle : bottles) {
            Material material = materialService.getFreeMaterialForBottle();
            if (material == null) throw new RuntimeException("Нет свободного материала для добавления в бутылку");
            AddMaterialToOvumContainer addMaterialToOvumContainer = new AddMaterialToOvumContainer(new AddMaterialToOvumContainerKey(material.getId(), bottle.getId()), material, bottle, new Date());
            addMaterialToOvumContainerService.save(addMaterialToOvumContainer);
        }
        log.debug("Свиные лоскуты помещены в бутыли");
        //На бутыль наклеивают этикетку со сведениями о заказе, группе Бокановского
        for (OvumContainer bottle : bottles) {
            Integer bokanovskyGroup = bokanovskyGroupMagicCalc(order);
            Label label = new Label(order, bottle, bokanovskyGroup);
            labelService.save(label);
        }
        log.debug("Этикетки расклеены");

        //В зависимости от запроса Предопределителя каждую бутыль определяют на один из пятнадцати конвейеров
        //todo: распределение на нужные дорожки в зависимости от FutureJobType'ов
        Machine lane = machineService.getMachineByName(MachineName.Дорожка_01);
        for (OvumContainer bottle : bottles) {
            UseMachineByOvumContainer interactBottleWithRoad = new UseMachineByOvumContainer(new UseMachineByOvumContainerKey(lane.getId(), bottle.getId(), new Date()), lane, bottle, new Date(), null);
            useMachineByOvumContainerService.save(interactBottleWithRoad);
        }
        log.debug("Бутыли распределены на {}", lane.getName());

        //Бутыли отправляются в Эмбрионарий на 267 суток
        room = roomService.findRoomByName("Эмбрионарий");
        if (room == null)
            throw new RuntimeException("Эмбрионарий не найден...");
        for (OvumContainer bottle : bottles) {
            MoveOvumContainerToRoom moveOvumContainerToRoom = new MoveOvumContainerToRoom(new MoveOvumContainerToRoomKey(bottle.getId(), room.getId(), new Date()), bottle, room);
            moveOvumContainerToRoomService.save(moveOvumContainerToRoom);
        }
        log.debug("Бутыли прибыли в эмбрионарий");
        for (Ovum ovum : ovumList) {
            ovum.setEmbryoTime(new Date());
            ovumService.save(ovum);
        }
        log.debug("Все яйцеклетки теперь эмбрионы");

        //Младенцев привозят в Младопитомник
        room = roomService.findRoomByName("Младопитомник");
        if (room == null)
            throw new RuntimeException("Младопитомник не найден...");
        for (OvumContainer bottle : bottles) {
            MoveOvumContainerToRoom moveOvumContainerToRoom = new MoveOvumContainerToRoom(new MoveOvumContainerToRoomKey(bottle.getId(), room.getId(), new Date()), bottle, room);
            moveOvumContainerToRoomService.save(moveOvumContainerToRoom);
        }
        log.debug("Бутыли прибыли в младопитомник");

        for (Ovum ovum : ovumList) {
            ovum.setBabyTime(new Date());
            ovumService.save(ovum);
        }
        log.debug("Все яйцеклетки теперь эмбрионы");
        return new ResponseEntity<>("Третий этап успешно завершён", HttpStatus.OK);

    }

    private Integer bokanovskyGroupMagicCalc(Order order) {
        return Arrays.asList(new String[]{"Alpha", "Beta"}).contains(order.getCaste().name()) ? null : (int) (Math.random() * (96 - 72) + 72);
    }
}
