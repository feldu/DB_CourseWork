package db.coursework.repositories;

import db.coursework.entities.MoveOvumContainerToRoom;
import db.coursework.entities.keys.MoveOvumContainerToRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveOvumContainerToRoomRepository extends JpaRepository<MoveOvumContainerToRoom, MoveOvumContainerToRoomKey> {
    List<MoveOvumContainerToRoom> findAllByOvumContainer_Id(Long id);
}
