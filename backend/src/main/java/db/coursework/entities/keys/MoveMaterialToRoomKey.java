package db.coursework.entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class MoveMaterialToRoomKey implements Serializable {

    @Column(name = "material_id")
    Long materialId;

    @Column(name = "room_id")
    Long roomId;

    @Column(name = "arrival_time")
    Date arrivalTime;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
