package java.db.coursework.repositories;

import db.coursework.entities.FutureJobType;
import db.coursework.entities.enums.FutureJobTypeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FutureJobTypeRepository extends JpaRepository<FutureJobType, Long> {
    Optional<FutureJobType> findByName(FutureJobTypeName name);
}
