package db.coursework.services;

import db.coursework.entities.Human;
import db.coursework.repositories.HumanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HumanService {
    private final HumanRepository humanRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    public boolean saveHuman(Human human) {
        humanRepository.save(human);
        log.debug("Human {} saved in DB", human);
        return true;
    }
}
