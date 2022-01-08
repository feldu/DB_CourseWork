package db.coursework.repositories;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHuman(Human human);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE \"order\" SET is_processing = :value WHERE id = :id",
            nativeQuery = true)
    void updateIsProcessingById(@Param(value = "value") Boolean value, @Param(value = "id") Long id);
}
