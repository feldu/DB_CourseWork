package db.coursework.entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class UseMachineByOvumContainerKey implements Serializable {
    @Column(name = "machine_id")
    Long machineId;

    @Column(name = "ovum_container_id")
    Long ovumContainerId;

    @Column(name = "start_time")
    Date startTime;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
