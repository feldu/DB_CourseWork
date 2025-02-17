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
@Entity(name = "removal_volunteer_for_defect")
@NoArgsConstructor
@Table(name = "removal_volunteer_for_defect")
public class RemovalVolunteerForDefect {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class RemovalVolunteerForDefectKey implements Serializable {
        @Column(name = "defect_id")
        Long defectId;

        @Column(name = "ovum_id")
        Long ovumId;

        @Column(name = "removal_time")
        Date removalTime;
    }

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
