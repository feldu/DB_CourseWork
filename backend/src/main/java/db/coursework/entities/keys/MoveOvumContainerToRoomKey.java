package db.coursework.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MoveOvumContainerToRoomKey implements Serializable {

    @Column(name = "ovum_container_id")
    Long ovumContainerId;

    @Column(name = "room_id")
    Long roomId;

    @Column(name = "arrival_time")
    Date arrivalTime;
}
