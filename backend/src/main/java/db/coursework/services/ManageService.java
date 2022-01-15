package db.coursework.services;

import db.coursework.entities.*;
import db.coursework.entities.enums.OvumContainerName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class ManageService {

    private final OvumService ovumService;
    private final OvumContainerService ovumContainerService;
    private final RoleService roleService;
    private final UserService userService;
    private final MaterialService materialService;

    @Autowired
    public ManageService(OvumService ovumService, OvumContainerService ovumContainerService, RoleService roleService, UserService userService, MaterialService materialService) {
        this.ovumService = ovumService;
        this.ovumContainerService = ovumContainerService;
        this.roleService = roleService;
        this.userService = userService;
        this.materialService = materialService;
    }

    @Transactional
    public void addFreeOvum(Long ovumCount) {
        for (int i = 0; i < ovumCount; i++) {
            Role role = roleService.findByName("ROLE_VOLUNTEER");
            Human human = new Human("Добровольный чел №" + (int) (Math.random() * 1000000), Collections.singleton(role));
            Ovum ovum = new Ovum();
            ovum.setVolunteer(human);
            ovumService.save(ovum);
        }
    }

    @Transactional
    public void addOvumContainers(Long count, String name) {
        for (int i = 0; i < count; i++) {
            OvumContainer bottle = new OvumContainer();
            bottle.setName(OvumContainerName.valueOf(name));
            ovumContainerService.save(bottle);
        }
    }

    @Transactional
    public void addMaterial(Long count) {
        for (int i = 0; i < count; i++) {
            Material material = new Material();
            material.setName("Свиной лоскут");
            int size = (int) (Math.random() * (100 - 50)) + 50;
            material.setRequiredSize(size);
            material.setCurrentSize(size);
            material.setQualityPartsPercentage(100);
            materialService.save(material);
        }
    }

    @Transactional
    public void addAdmin(String username) {
        Role role = roleService.findByName("ROLE_ADMIN");
        if (role == null) throw new RuntimeException("Роль не найдена");
        User user = userService.loadUserByUsername(username);
        if (user == null) throw new RuntimeException("Юзер не найден");
        user.getHuman().getRoles().add(role);
        userService.updateUser(user);
    }
}
