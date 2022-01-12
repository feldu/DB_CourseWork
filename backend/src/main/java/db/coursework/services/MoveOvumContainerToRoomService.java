package db.coursework.services;

import db.coursework.entities.MoveOvumContainerToRoom;
import db.coursework.repositories.MoveOvumContainerToRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveOvumContainerToRoomService {
    private final MoveOvumContainerToRoomRepository moveOvumContainerToRoomRepository;

    @Autowired
    public MoveOvumContainerToRoomService(MoveOvumContainerToRoomRepository moveOvumContainerToRoomRepository) {
        this.moveOvumContainerToRoomRepository = moveOvumContainerToRoomRepository;
    }

    public MoveOvumContainerToRoom save(MoveOvumContainerToRoom moveOvumContainerToRoom) {
        return moveOvumContainerToRoomRepository.save(moveOvumContainerToRoom);
    }
}
