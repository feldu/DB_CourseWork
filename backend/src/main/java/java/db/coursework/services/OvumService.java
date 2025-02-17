package java.db.coursework.services;

import db.coursework.entities.Ovum;
import db.coursework.entities.OvumContainer;
import db.coursework.repositories.OrderRepository;
import db.coursework.repositories.OvumContainerRepository;
import db.coursework.repositories.OvumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OvumService {
    private final OvumRepository ovumRepository;
    private final OrderRepository orderRepository;
    private final OvumContainerRepository ovumContainerRepository;

    @Autowired
    public OvumService(OvumRepository ovumRepository, OrderRepository orderRepository, OvumContainerRepository ovumContainerRepository) {
        this.ovumRepository = ovumRepository;
        this.orderRepository = orderRepository;
        this.ovumContainerRepository = ovumContainerRepository;
    }

    public Long getOvumCountByOrderId(Long id) {
        return ovumRepository.countAllByOrder_Id(id);
    }

    public List<Ovum> findAllByOrder_Id(Long id) {
        return ovumRepository.findAllOvumByOrder_Id(id);
    }

    public List<Ovum> findAllByHumanId(Long id) {
        return ovumRepository.findAllByVolunteerId(id);
    }

    public List<Ovum> findAllOvumByOrder_Id(Long orderId) {
        return ovumRepository.findAllOvumByOrder_Id(orderId);
    }

    @Transactional
    public void bindFreeOvum(Long orderId, Long count) {
        ovumRepository.bindFreeOvumToOrder(orderId, count);
        OvumContainer freeOvumreceiver = ovumContainerRepository.getFreeOvumreceiver();
        if (freeOvumreceiver == null)
            throw new RuntimeException("Не удалось начать выполнение заказа: нет свободных яйцеприемников");
        orderRepository.updateIsProcessingById(true, orderId);
        ovumRepository.bindOrderOvumToOvumContainer(orderId, freeOvumreceiver);
    }

    public void updateOvumByOvumDTOFields(long id, boolean isBud, Date fertilizationTime, Date embryoTime, Date babyTime) {
        ovumRepository.updateOvumByOvumDTOFields(id, isBud, fertilizationTime, embryoTime, babyTime);
    }

    public void updateOvumInOvumContainerByFertilizationTime(Long ovumContainerId, Date fertilizationTime) {
        ovumRepository.updateOvumInOvumContainerByFertilizationTime(ovumContainerId, fertilizationTime);
    }

    public void removeExtraOvumByOrderId(Long orderId, Long count) {
        ovumRepository.removeExtraOvumByOrderId(orderId, count);
    }

    public Ovum save(Ovum ovum) {
        return ovumRepository.save(ovum);
    }
}