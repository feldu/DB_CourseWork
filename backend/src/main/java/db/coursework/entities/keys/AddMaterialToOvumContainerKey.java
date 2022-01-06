package db.coursework.entities.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddMaterialToOvumContainerKey implements Serializable {
    @Column(name = "material_id")
    Long materialId;

    @Column(name = "ovum_container_id")
    Long ovumContainerId;
}
