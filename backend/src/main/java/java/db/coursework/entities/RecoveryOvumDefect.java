package java.db.coursework.entities;

import db.coursework.entities.Defect;
import db.coursework.entities.Human;
import db.coursework.entities.Ovum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "recovery_ovum_defect")
@NoArgsConstructor
@Table(name = "recovery_ovum_defect")
public class RecoveryOvumDefect {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class RecoveryOvumDefectKey implements Serializable {
        @Column(name = "ovum_id")
        Long ovumId;

        @Column(name = "defect_id")
        Long defectId;

        @Column(name = "recovery_time")
        Date recoveryTime;
    }

    @EmbeddedId
    RecoveryOvumDefectKey id;

    @ManyToOne
    @MapsId("ovumId")
    @JoinColumn(name = "ovum_id")
    Ovum ovum;

    @ManyToOne
    @MapsId("defectId")
    @JoinColumn(name = "defect_id")
    Defect defect;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "surgeon_id")
    private Human surgeon;
}
