package db.coursework.controllers;

import db.coursework.dto.CasteDTO;
import db.coursework.dto.FutureJobTypesDTO;
import db.coursework.dto.UserDTO;
import db.coursework.entities.Role;
import db.coursework.entities.User;
import db.coursework.entities.enums.FutureJobTypeName;
import db.coursework.entities.enums.OrderCaste;
import db.coursework.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/authenticated")
    public ResponseEntity<UserDTO> getUserInfo(Authentication authentication) {
        try {
            User user = userService.loadUserByUsername(authentication.getName());
            Role role = new ArrayList<>(user.getHuman().getRoles()).get(0);
            UserDTO userDto = new UserDTO(user.getUsername(), user.getPassword(), user.getHuman().getFullname(), role.getName().split("_")[1]);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((UserDTO) null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/future-job-types")
    public ResponseEntity<List<FutureJobTypesDTO>> getAllFutureJobTypesNames() {
        try {
            List<FutureJobTypeName> futureJobTypeNameList = Arrays.asList(FutureJobTypeName.values());
            List<FutureJobTypesDTO> futureJobTypesDTOList = futureJobTypeNameList.stream().map(type -> new FutureJobTypesDTO(type.name(), type.getLabel())).collect(Collectors.toList());
            return new ResponseEntity<>(futureJobTypesDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/castes")
    public ResponseEntity<List<CasteDTO>> getCaste() {
        try {
            List<OrderCaste> orderCastes = Arrays.asList(OrderCaste.values());
            List<CasteDTO> casteDTOList = orderCastes.stream().map(type -> new CasteDTO(type.name(), type.getLabel())).collect(Collectors.toList());
            return new ResponseEntity<>(casteDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
