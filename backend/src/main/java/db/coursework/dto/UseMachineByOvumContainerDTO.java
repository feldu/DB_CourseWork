package db.coursework.dto;

import db.coursework.entities.Machine;
import db.coursework.entities.OvumContainer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class UseMachineByOvumContainerDTO {
    @NotNull
    Machine machine;
    @NotNull
    OvumContainer ovumContainer;
    @NotNull
    Date startTime;
    @NotNull
    Date endTime;
    Integer totalBudsCount;
}
