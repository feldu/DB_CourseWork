package java.db.coursework.repositories;

import db.coursework.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findFirstRoomByName(String name);
}
