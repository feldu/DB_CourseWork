package java.db.coursework.services;

import db.coursework.entities.FutureJobType;
import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.OvumContainer;
import db.coursework.entities.enums.FutureJobTypeName;
import db.coursework.entities.enums.OrderCaste;
import db.coursework.exception.DataNotFoundException;
import db.coursework.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FutureJobTypeRepository futureJobTypeRepository;
    private final OvumContainerRepository ovumContainerRepository;
    private final MoveOvumContainerToRoomRepository moveOvumContainerToRoomRepository;
    private final UseMachineByOvumContainerRepository useMachineByOvumContainerRepository;
    private final AddMaterialToOvumContainerRepository addMaterialToOvumContainerRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, FutureJobTypeRepository futureJobTypeRepository, OvumContainerRepository ovumContainerRepository, MoveOvumContainerToRoomRepository moveOvumContainerToRoomRepository, UseMachineByOvumContainerRepository useMachineByOvumContainerRepository, AddMaterialToOvumContainerRepository addMaterialToOvumContainerRepository) {
        this.orderRepository = orderRepository;
        this.futureJobTypeRepository = futureJobTypeRepository;
        this.ovumContainerRepository = ovumContainerRepository;
        this.moveOvumContainerToRoomRepository = moveOvumContainerToRoomRepository;
        this.useMachineByOvumContainerRepository = useMachineByOvumContainerRepository;
        this.addMaterialToOvumContainerRepository = addMaterialToOvumContainerRepository;
    }

    public List<Order> findAllOrdersByHuman(Human human) {
        return orderRepository.findAllByHuman(human);
    }

    public Order saveOrderFromRequest(Human human, Integer count, String caste, List<String> types) {
        Order order = new Order(human, count, OrderCaste.valueOf(caste));
        order.setProcessing(false);
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
        return futureJobTypeRepository.findByName(name).orElseThrow(() -> new DataNotFoundException("Future Job Type not found"));
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order not found"));
    }

    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteOrderById(id);
    }

    @Transactional
    public void removeOrderInfo(Long orderId) {
        List<OvumContainer> ovumContainerList = ovumContainerRepository.getAllOrderOvumContainers(orderId);
        for (OvumContainer ovumContainer : ovumContainerList) {
            moveOvumContainerToRoomRepository.deleteByOvumContainerId(ovumContainer.getId());
            useMachineByOvumContainerRepository.deleteByOvumContainerId(ovumContainer.getId());
            addMaterialToOvumContainerRepository.deleteByOvumContainerId(ovumContainer.getId());
        }
        log.debug("Информация о контейнерах заказа {} очищена", orderId);
        deleteOrderById(orderId);
        log.debug("Заказ {} удалён", orderId);
    }
}
