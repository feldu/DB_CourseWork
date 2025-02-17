package java.db.coursework.services;

import db.coursework.entities.Room;
import db.coursework.exception.DataNotFoundException;
import db.coursework.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room findRoomByName(String name) {
        return roomRepository.findFirstRoomByName(name).orElseThrow(() -> new DataNotFoundException("Room not found"));
    }

}
