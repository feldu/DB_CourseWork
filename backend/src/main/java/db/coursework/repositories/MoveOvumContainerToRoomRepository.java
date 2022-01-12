package db.coursework.repositories;

import db.coursework.entities.MoveOvumContainerToRoom;
import db.coursework.entities.keys.MoveOvumContainerToRoomKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveOvumContainerToRoomRepository extends JpaRepository<MoveOvumContainerToRoom, MoveOvumContainerToRoomKey> {
}
