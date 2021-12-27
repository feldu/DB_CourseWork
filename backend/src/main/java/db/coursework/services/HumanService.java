package db.coursework.services;

import db.coursework.entities.Human;
import db.coursework.entities.HumanRole;
import db.coursework.repositories.HumanRepository;
import db.coursework.repositories.HumanRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HumanService {
    private final HumanRepository humanRepository;
    private final HumanRoleRepository humanRoleRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository, HumanRoleRepository humanRoleRepository) {
        this.humanRepository = humanRepository;
        this.humanRoleRepository = humanRoleRepository;
    }

    public boolean saveHuman(Human human) {
        humanRepository.save(human);
        log.debug("Human {} saved in DB", human);
        return true;
    }

    public boolean saveHumanRole(HumanRole humanRole) {
        humanRoleRepository.save(humanRole);
        return true;
    }
}
