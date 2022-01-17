package db.coursework.repositories;

import db.coursework.entities.UseMachineByOvumContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UseMachineByOvumContainerRepository extends JpaRepository<UseMachineByOvumContainer, UseMachineByOvumContainer.UseMachineByOvumContainerKey> {
    List<UseMachineByOvumContainer> findAllByOvumContainer_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from use_machine_by_ovum_container where ovumContainer.id = :id")
    void deleteByOvumContainerId(@Param(value = "id") Long id);
}
