package db.coursework.services;

import db.coursework.entities.FutureJobType;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.enums.FutureJobTypeName;
import db.coursework.entities.enums.OrderCaste;
import db.coursework.repositories.FutureJobTypeRepository;
import db.coursework.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Order> findAllOrdersByHuman(Human human) {
        return orderRepository.findAllByHuman(human);
    }

    public Order saveOrderFromRequest(Human human, Integer count, String caste, List<String> types) {
        Order order = new Order(human, count, OrderCaste.valueOf(caste));
        for (String type : types) {
            if (type != null) {
                FutureJobType currentFutureJobType = getFutureJobTypeFromDB(type);
                currentFutureJobType.getOrders().add(order);
                order.getFutureJobTypes().add(currentFutureJobType);
            }
        }
        return saveOrder(order);
    }

    private FutureJobType getFutureJobTypeFromDB(String type) {
        FutureJobType currentFutureJobType = findFutureJobTypeByName(FutureJobTypeName.valueOf(type));
        if (currentFutureJobType == null)
            currentFutureJobType = futureJobTypeRepository.save(new FutureJobType(FutureJobTypeName.valueOf(type)));
        return currentFutureJobType;
    }

    public Order saveOrder(Order order) {
        log.debug("Add order {} to DB.", order);
        return orderRepository.save(order);
    }

    public FutureJobType findFutureJobTypeByName(FutureJobTypeName name) {
        return futureJobTypeRepository.findByName(name);
    }

    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
