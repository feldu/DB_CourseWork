package db.coursework.repositories;

import db.coursework.entities.UseMachineByOvumContainer;
import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UseMachineByOvumContainerRepository extends JpaRepository<UseMachineByOvumContainer, UseMachineByOvumContainerKey> {
    List<UseMachineByOvumContainer> findAllByOvumContainer_Id(Long id);
}
