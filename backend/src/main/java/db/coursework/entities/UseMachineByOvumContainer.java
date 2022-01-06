package db.coursework.entities;

import db.coursework.entities.keys.UseMachineByOvumContainerKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "use_machine_by_ovum_container")
@NoArgsConstructor
@Table(name = "use_machine_by_ovum_container")
public class UseMachineByOvumContainer {
    @EmbeddedId
    UseMachineByOvumContainerKey id;

    @ManyToOne
    @MapsId("machineId")
    @JoinColumn(name = "machine_id")
    Machine machine;

    @ManyToOne
    @MapsId("ovumContainerId")
    @JoinColumn(name = "ovum_container_id")
    OvumContainer ovumContainer;

    @Column(name = "end_time")
    Date endTime;

    @Column(name = "total_buds_count")
    int totalBudsCount;
}