package db.coursework.repositories;

import db.coursework.entities.Order;
import db.coursework.entities.Ovum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OvumRepository extends JpaRepository<Ovum, Long> {
    List<Ovum> findAllByOvumContainer_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE ovum SET order_id = :order_id WHERE id IN (SELECT id FROM ovum WHERE order_id IS NULL LIMIT :count)",
            nativeQuery = true)
    void bindFreeOvumToOrder(@Param(value = "order_id") Long orderId, @Param(value = "count") Long count);

    Long countAllByOrder(Order order);
}
