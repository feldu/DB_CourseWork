package db.coursework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity(name = "material")
@NoArgsConstructor
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Min(value = 0)
    @Column(name = "current_size")
    private int currentSize;

    @NotNull
    @Min(value = 0)
    @Column(name = "required_size")
    private int requiredSize;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column(name = "quality_parts_percentage")
    private float qualityPartsPercentage;
}
