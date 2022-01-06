package db.coursework.repositories;

import db.coursework.entities.Order;
import db.coursework.entities.Ovum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvumRepository extends JpaRepository<Ovum, Long> {
    List<Ovum> findAllByOvumContainer_Id(Long id);

    Long countAllByOrder(Order order);
}
