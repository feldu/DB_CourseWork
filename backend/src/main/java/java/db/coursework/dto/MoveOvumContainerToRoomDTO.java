package java.db.coursework.dto;

import db.coursework.entities.OvumContainer;
import db.coursework.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class MoveOvumContainerToRoomDTO {
    @NotNull
    OvumContainer ovumContainer;
    @NotNull
    Room room;
    @NotNull
    Date arrivalTime;
}
