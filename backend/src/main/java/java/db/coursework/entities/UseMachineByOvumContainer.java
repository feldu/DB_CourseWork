package java.db.coursework.entities;

import db.coursework.entities.Machine;
import db.coursework.entities.OvumContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "use_machine_by_ovum_container")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "use_machine_by_ovum_container")
public class UseMachineByOvumContainer {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class UseMachineByOvumContainerKey implements Serializable {
        @Column(name = "machine_id")
        Long machineId;

        @Column(name = "ovum_container_id")
        Long ovumContainerId;

        @Column(name = "start_time")
        Date startTime;
    }

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
    Integer totalBudsCount;
}