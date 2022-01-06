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
public class UseMachineByOvumContainerKey implements Serializable {
    @Column(name = "machine_id")
    Long machineId;

    @Column(name = "ovum_container_id")
    Long ovumContainerId;

    @Column(name = "start_time")
    Date startTime;
}
