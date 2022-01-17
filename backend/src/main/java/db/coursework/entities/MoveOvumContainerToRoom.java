package db.coursework.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "move_ovum_container_to_room")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "move_ovum_container_to_room")
public class MoveOvumContainerToRoom {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class MoveOvumContainerToRoomKey implements Serializable {

        @Column(name = "ovum_container_id")
        Long ovumContainerId;

        @Column(name = "room_id")
        Long roomId;

        @Column(name = "arrival_time")
        Date arrivalTime;
    }

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
