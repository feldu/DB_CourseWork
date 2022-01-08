package db.coursework.services;

import db.coursework.entities.Order;
import db.coursework.repositories.OrderRepository;
import db.coursework.repositories.OvumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OvumService {
    private final OvumRepository ovumRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OvumService(OvumRepository ovumRepository, OrderRepository orderRepository) {
        this.ovumRepository = ovumRepository;
        this.orderRepository = orderRepository;
    }

    public Long getOvumCountByOvumContainerAndFertilizationTime(Order order) {
        return ovumRepository.countAllByOrder(order);
    }

    @Transactional
    public void bindFreeOvumToOrder(Long orderId, Long count) {
        ovumRepository.bindFreeOvumToOrder(orderId, count);
        orderRepository.updateIsProcessingById(true, orderId);
    }
}
