package db.coursework.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "add_material_to_ovum_container")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "add_material_to_ovum_container")
@EqualsAndHashCode
public class AddMaterialToOvumContainer {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class AddMaterialToOvumContainerKey implements Serializable {
        @Column(name = "material_id")
        Long materialId;

        @Column(name = "ovum_container_id")
        Long ovumContainerId;
    }

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
