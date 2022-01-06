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
public class RemovalVolunteerForDefectKey implements Serializable {
    @Column(name = "defect_id")
    Long defectId;

    @Column(name = "ovum_id")
    Long ovumId;

    @Column(name = "removal_time")
    Date removalTime;
}
