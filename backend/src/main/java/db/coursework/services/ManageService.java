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
    private final MaterialService materialService;

    @Autowired
    public ManageService(OvumService ovumService, OvumContainerService ovumContainerService, RoleService roleService, MaterialService materialService) {
        this.ovumService = ovumService;
        this.ovumContainerService = ovumContainerService;
        this.roleService = roleService;
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
    public void addMaterialAndCut(Long count, String name) {
        for (int i = 0; i < count; i++) {
            Material material = new Material();
            material.setName(name);
            material.setRequiredSize((int) (Math.random() * (100 - 50)) + 50);
            material.setCurrentSize((int) (Math.random() * (1000 - 100)) + 100);
            material.setQualityPartsPercentage((float) (Math.random() * (100 - 60) + 60));
            Material saved = materialService.save(material);
            try {
                materialService.cutMaterial(Math.toIntExact(saved.getId()), "Свиной лоскут");
            } catch (Exception ignored) {
            }
        }
    }
}
