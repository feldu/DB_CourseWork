package db.coursework.repositories;

import db.coursework.entities.AddMaterialToOvumContainer;
import db.coursework.entities.keys.AddMaterialToOvumContainerKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddMaterialToOvumContainerRepository extends JpaRepository<AddMaterialToOvumContainer, AddMaterialToOvumContainerKey> {
}
