package java.db.coursework.dto;

import db.coursework.entities.Material;
import db.coursework.entities.OvumContainer;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class AddMaterialToOvumContainerDTO {
    @NotNull
    Material material;
    @NotNull
    OvumContainer ovumContainer;
    @NotNull
    Date insertionTime;
}
