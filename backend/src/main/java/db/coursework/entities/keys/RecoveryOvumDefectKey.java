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
public class RecoveryOvumDefectKey implements Serializable {
    @Column(name = "ovum_id")
    Long ovumId;

    @Column(name = "defect_id")
    Long defectId;

    @Column(name = "recovery_time")
    Date recoveryTime;
}
