package db.coursework.controllers;

import db.coursework.entities.Machine;
import db.coursework.entities.OvumContainer;
import db.coursework.entities.UseMachineByOvumContainer;
import db.coursework.entities.enums.MachineName;
import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import db.coursework.services.MachineService;
import db.coursework.services.OvumContainerService;
import db.coursework.services.OvumService;
import db.coursework.services.UseMachineByOvumContainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/admin/start")
public class AdminStarterController {
    private final OvumContainerService ovumContainerService;
    private final OvumService ovumService;
    private final MachineService machineService;
    private final UseMachineByOvumContainerService useMachineByOvumContainerService;

    @Autowired
    public AdminStarterController(OvumContainerService ovumContainerService, OvumService ovumService, MachineService machineService, UseMachineByOvumContainerService useMachineByOvumContainerService) {
        this.ovumContainerService = ovumContainerService;
        this.ovumService = ovumService;
        this.machineService = machineService;
        this.useMachineByOvumContainerService = useMachineByOvumContainerService;
    }

    @PostMapping("/first_step")
    public ResponseEntity<String> startFirstStep(@RequestBody Map<String, Long> payload) {
        try {
            Long orderId = payload.get("orderId");
            //Яйцеприемник в бульон со сперматазоидами
            OvumContainer ovumContainer = ovumContainerService.getOrderOvumreceiver(orderId);
            Machine machine = machineService.getMachineByName(MachineName.Бульон_со_сперматозоидами);
            UseMachineByOvumContainer useMachineByOvumContainer = new UseMachineByOvumContainer(new UseMachineByOvumContainerKey(machine.getId(), ovumContainer.getId(), new Date()), machine, ovumContainer, null, null);
            useMachineByOvumContainerService.save(useMachineByOvumContainer);
            //После оплодотворения яйцеприёмник вынимают из бульона
            ovumService.updateOvumInOvumContainerByFertilizationTime(ovumContainer.getId(), new Date());
            useMachineByOvumContainer.setEndTime(new Date());
            useMachineByOvumContainerService.save(useMachineByOvumContainer);
            return new ResponseEntity<>("Первый этап успешно завершён", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
