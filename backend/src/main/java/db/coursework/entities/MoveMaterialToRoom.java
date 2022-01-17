package db.coursework.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "move_material_to_room")
@NoArgsConstructor
@Table(name = "move_material_to_room")
@EqualsAndHashCode
public class MoveMaterialToRoom {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class MoveMaterialToRoomKey implements Serializable {

        @Column(name = "material_id")
        Long materialId;

        @Column(name = "room_id")
        Long roomId;

        @Column(name = "arrival_time")
        Date arrivalTime;
    }

    @EmbeddedId
    MoveMaterialToRoomKey id;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne
    @MapsId("roomId")
    @JoinColumn(name = "room_id")
    Room room;
}
