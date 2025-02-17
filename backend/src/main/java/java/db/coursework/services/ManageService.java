package java.db.coursework.services;

import db.coursework.entities.*;
import db.coursework.entities.enums.OvumContainerName;
import db.coursework.repositories.HumanRepository;
import db.coursework.services.MaterialService;
import db.coursework.services.OvumContainerService;
import db.coursework.services.OvumService;
import db.coursework.services.RoleService;
import db.coursework.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Slf4j
@Service
public class ManageService {

    private final db.coursework.services.OvumService ovumService;
    private final OvumContainerService ovumContainerService;
    private final db.coursework.services.RoleService roleService;
    private final db.coursework.services.UserService userService;
    private final MaterialService materialService;
    private final HumanRepository humanRepository;

    @Autowired
    public ManageService(OvumService ovumService, OvumContainerService ovumContainerService, RoleService roleService, UserService userService, MaterialService materialService, HumanRepository humanRepository) {
        this.ovumService = ovumService;
        this.ovumContainerService = ovumContainerService;
        this.roleService = roleService;
        this.userService = userService;
        this.materialService = materialService;
        this.humanRepository = humanRepository;
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
    public void addFreeOvum(Human human) {
        if (ovumService.findAllByHumanId(human.getId()).size() > 0) {
            throw new RuntimeException("Яйцеклетка уже была вырезана");
        }
        Ovum ovum = new Ovum();
        ovum.setVolunteer(human);
        ovumService.save(ovum);
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
    public void addMaterial(int size) {
        Material material = new Material();
        material.setName("Свиной лоскут");
        material.setRequiredSize(size);
        material.setCurrentSize(size);
        material.setQualityPartsPercentage(100);
        materialService.save(material);
    }

    @Transactional
    public void addAdmin(String username) {
        Role role = roleService.findByName("ROLE_ADMIN");
        User user = userService.loadUserByUsername(username);
        user.getHuman().getRoles().add(role);
        userService.updateUser(user);
    }
}