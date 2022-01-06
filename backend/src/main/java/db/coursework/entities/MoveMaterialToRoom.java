package db.coursework.entities;

import db.coursework.entities.keys.MoveMaterialToRoomKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "move_material_to_room")
@NoArgsConstructor
@Table(name = "move_material_to_room")
public class MoveMaterialToRoom {
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
