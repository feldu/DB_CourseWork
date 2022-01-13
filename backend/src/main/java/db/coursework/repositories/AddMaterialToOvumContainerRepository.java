package db.coursework.repositories;

import db.coursework.entities.AddMaterialToOvumContainer;
import db.coursework.entities.keys.AddMaterialToOvumContainerKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddMaterialToOvumContainerRepository extends JpaRepository<AddMaterialToOvumContainer, AddMaterialToOvumContainerKey> {
    List<AddMaterialToOvumContainer> findAllByOvumContainer_Id(Long id);
}
