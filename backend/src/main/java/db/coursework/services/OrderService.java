package db.coursework.services;

import db.coursework.entities.FutureJobType;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.repositories.FutureJobTypeRepository;
import db.coursework.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FutureJobTypeRepository futureJobTypeRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, FutureJobTypeRepository futureJobTypeRepository) {
        this.orderRepository = orderRepository;
        this.futureJobTypeRepository = futureJobTypeRepository;
    }

    public Order saveOrderFromRequest(Human human, Integer count, String caste, String[] types) {
        Order order = new Order(human, count, caste);
        for (String type : types) {
            FutureJobType currentFutureJobType = findFutureJobTypeByName(type);
            if (currentFutureJobType != null) {
                currentFutureJobType.getOrders().add(order);
                order.getFutureJobTypes().add(currentFutureJobType);
            }
        }
        return saveOrder(order);
    }

    public Order saveOrder(Order order) {
        log.debug("Add order {} to DB.", order);
        return orderRepository.save(order);
    }

    public FutureJobType findFutureJobTypeByName(String name) {
        return futureJobTypeRepository.findByName(name);
    }
}
