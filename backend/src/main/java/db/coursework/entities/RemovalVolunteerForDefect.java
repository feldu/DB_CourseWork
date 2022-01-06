package db.coursework.entities;

import db.coursework.entities.keys.RemovalVolunteerForDefectKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "removal_volunteer_for_defect")
@NoArgsConstructor
@Table(name = "removal_volunteer_for_defect")
public class RemovalVolunteerForDefect {
    @EmbeddedId
    RemovalVolunteerForDefectKey id;

    @ManyToOne
    @MapsId("defectId")
    @JoinColumn(name = "defect_id")
    Defect defect;

    @ManyToOne
    @MapsId("ovumId")
    @JoinColumn(name = "ovum_id")
    Ovum ovum;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id")
    private Human reviewer;
}
