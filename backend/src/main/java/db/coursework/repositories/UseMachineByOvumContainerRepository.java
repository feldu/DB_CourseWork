package db.coursework.repositories;

import db.coursework.entities.UseMachineByOvumContainer;
import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UseMachineByOvumContainerRepository extends JpaRepository<UseMachineByOvumContainer, UseMachineByOvumContainerKey> {

}
