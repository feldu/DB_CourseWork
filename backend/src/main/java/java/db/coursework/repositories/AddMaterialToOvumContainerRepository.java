package java.db.coursework.repositories;

import db.coursework.entities.AddMaterialToOvumContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AddMaterialToOvumContainerRepository extends JpaRepository<AddMaterialToOvumContainer, AddMaterialToOvumContainer.AddMaterialToOvumContainerKey> {
    List<AddMaterialToOvumContainer> findAllByOvumContainer_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from add_material_to_ovum_container where ovumContainer.id = :id")
    void deleteByOvumContainerId(@Param(value = "id") Long id);
}
