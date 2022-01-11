package db.coursework.services;

import db.coursework.entities.OvumContainer;
import db.coursework.repositories.OvumContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OvumContainerService {

    private final OvumContainerRepository ovumContainerRepository;

    @Autowired
    public OvumContainerService(OvumContainerRepository ovumContainerRepository) {
        this.ovumContainerRepository = ovumContainerRepository;
    }


    public OvumContainer getOrderOvumreceiver(Long orderId) {
        return ovumContainerRepository.getOrderOvumreceiver(orderId);
    }
}
