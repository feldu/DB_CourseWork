package db.coursework.entities;

import db.coursework.entities.keys.MoveOvumContainerToRoomKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "move_ovum_container_to_room")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "move_ovum_container_to_room")
public class MoveOvumContainerToRoom {
    @EmbeddedId
    MoveOvumContainerToRoomKey id;

    @ManyToOne
    @MapsId("ovumContainerId")
    @JoinColumn(name = "ovum_container_id")
    OvumContainer ovumContainer;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    Room room;
}
