package db.coursework.repositories;

import db.coursework.entities.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HumanRepository extends JpaRepository<Human, Long> {

    @Query(value = "SELECT h.id, h.full_name from human h " +
            "JOIN human_role h_r ON (h.id = h_r.human_id)" +
            "JOIN role r ON (r.id = h_r.role_id)" +
            "WHERE r.name = :name",
            nativeQuery = true)
    List<Human> findAllHumanByRoleName(@Param(value = "name") String name);
}
