package db.coursework.entities;

import db.coursework.entities.keys.RecoveryOvumDefectKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "recovery_ovum_defect")
@NoArgsConstructor
@Table(name = "recovery_ovum_defect")
public class RecoveryOvumDefect {
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
