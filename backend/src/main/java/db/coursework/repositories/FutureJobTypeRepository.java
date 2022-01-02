package db.coursework.repositories;

import db.coursework.entities.FutureJobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FutureJobTypeRepository extends JpaRepository<FutureJobType, Long> {
    FutureJobType findByName(String name);
}
