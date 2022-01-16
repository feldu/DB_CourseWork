package db.coursework.services;

import db.coursework.entities.Human;
import db.coursework.repositories.HumanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanService {
    private final HumanRepository humanRepository;

    @Autowired
    public HumanService(HumanRepository humanRepository) {
        this.humanRepository = humanRepository;
    }

    public List<Human> findAllHumanByRoleName(String name) {
        return humanRepository.findAllHumanByRoleName(name);
    }

    public Human findHumanById(Long id) {
        return humanRepository.findById(id).orElse(null);
    }

}
