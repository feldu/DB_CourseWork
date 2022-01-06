package db.coursework.services;

import db.coursework.entities.Order;
import db.coursework.repositories.OvumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OvumService {
    private final OvumRepository ovumRepository;

    @Autowired
    public OvumService(OvumRepository ovumRepository) {
        this.ovumRepository = ovumRepository;
    }

    public Long getOvumCountByOvumContainerAndFertilizationTime(Order order) {
        return ovumRepository.countAllByOrder(order);
    }

}
