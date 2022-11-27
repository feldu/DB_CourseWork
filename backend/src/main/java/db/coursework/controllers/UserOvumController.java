package db.coursework.controllers;

import db.coursework.entities.Ovum;
import db.coursework.services.OvumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/ovums")
public class UserOvumController {
    private final OvumService ovumService;

    @Autowired
    public UserOvumController(OvumService ovumService) {
        this.ovumService = ovumService;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<Ovum>> getOvumByOrder(@PathVariable Long orderId) {
        List<Ovum> ovumList = ovumService.findAllOvumByOrder_Id(orderId);
        log.debug("Sending {} ovum of {} order", ovumList.size(), orderId);
        return new ResponseEntity<>(ovumList, HttpStatus.OK);
    }
}
