package db.coursework.repositories;

import db.coursework.entities.Order;
import db.coursework.entities.Ovum;
import db.coursework.entities.OvumContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface OvumRepository extends JpaRepository<Ovum, Long> {
    List<Ovum> findAllOvumByOrder_Id(Long orderId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ovum SET order_id = :order_id WHERE id IN (SELECT id FROM ovum WHERE order_id IS NULL LIMIT :count)",
            nativeQuery = true)
    void bindFreeOvumToOrder(@Param(value = "order_id") Long orderId, @Param(value = "count") Long count);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ovum SET ovumContainer = :ovumContainer WHERE order.id = :orderId")
    void bindOrderOvumToOvumContainer(@Param(value = "orderId") Long orderId, @Param(value = "ovumContainer") OvumContainer ovumContainer);

    Long countAllByOrder(Order order);

    List<Ovum> findAllByOrder_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update ovum SET isBud = :isBud, " +
            " fertilizationTime = :fertilizationTime, " +
            "embryoTime = :embryoTime," +
            "babyTime = :babyTime " +
            "WHERE id = :id")
    void updateOvumByOvumDTOFields(@Param(value = "id") long id,
                                   @Param(value = "isBud") boolean isBud,
                                   @Param(value = "fertilizationTime") Date fertilizationTime,
                                   @Param(value = "embryoTime") Date embryoTime,
                                   @Param(value = "babyTime") Date babyTime);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update ovum SET fertilizationTime = :fertilizationTime WHERE ovumContainer.id = :ovumContainerId")
    void updateOvumInOvumContainerByFertilizationTime(@Param(value = "ovumContainerId") Long ovumContainerId, @Param(value = "fertilizationTime") Date fertilizationTime);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM ovum WHERE id IN (SELECT id FROM ovum WHERE order_id = :orderId LIMIT :count)",
            nativeQuery = true)
    void removeExtraOvumByOrderId(@Param(value = "orderId") Long orderId, @Param(value = "count") Long count);


}
