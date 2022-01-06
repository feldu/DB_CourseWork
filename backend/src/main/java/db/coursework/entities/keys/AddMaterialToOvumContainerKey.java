package db.coursework.entities.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AddMaterialToOvumContainerKey implements Serializable {
    @Column(name = "material_id")
    Long materialId;

    @Column(name = "ovum_container_id")
    Long ovumContainerId;

    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
