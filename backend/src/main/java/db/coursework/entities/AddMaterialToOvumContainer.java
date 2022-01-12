package db.coursework.entities;

import db.coursework.entities.keys.AddMaterialToOvumContainerKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "add_material_to_ovum_container")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "add_material_to_ovum_container")
public class AddMaterialToOvumContainer {
    @EmbeddedId
    AddMaterialToOvumContainerKey id;

    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    Material material;

    @ManyToOne
    @MapsId("ovumContainerId")
    @JoinColumn(name = "ovum_container_id")
    OvumContainer ovumContainer;

    @NotNull
    @Column(name = "insertion_time")
    private Date insertionTime;
}
