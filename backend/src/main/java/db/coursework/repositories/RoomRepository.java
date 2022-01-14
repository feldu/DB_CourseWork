package db.coursework.repositories;

import db.coursework.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findFirstRoomByName(String name);
}
