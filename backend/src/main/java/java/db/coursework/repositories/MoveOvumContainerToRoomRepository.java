package java.db.coursework.repositories;

import db.coursework.entities.MoveOvumContainerToRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MoveOvumContainerToRoomRepository extends JpaRepository<MoveOvumContainerToRoom, MoveOvumContainerToRoom.MoveOvumContainerToRoomKey> {
    List<MoveOvumContainerToRoom> findAllByOvumContainer_Id(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from move_ovum_container_to_room where ovumContainer.id = :id")
    void deleteByOvumContainerId(@Param(value = "id") Long id);
}
