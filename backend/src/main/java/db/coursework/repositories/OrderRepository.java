package db.coursework.repositories;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHuman(Human human);
}
