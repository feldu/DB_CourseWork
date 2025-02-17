package java.db.coursework.entities;

import db.coursework.entities.Machine;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "conditions_of_detention")
@NoArgsConstructor
@Table(name = "conditions_of_detention")
public class ConditionsOfDetention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @NotNull
    @DecimalMin(value = "0.6")
    @DecimalMax(value = "1.0")
    @Column(name = "oxygen_amount")
    private float oxygenAmount;

    @NotNull
    @DecimalMin(value = "0.6")
    @DecimalMax(value = "1.0")
    @Column(name = "nutrients_amount")
    private float nutrientsAmount;

    @NotNull
    @DecimalMin(value = "0.6")
    @DecimalMax(value = "1.0")
    @Column(name = "sex_hormone_dose")
    private float sexHormoneDose;

    @NotNull
    @DecimalMin(value = "15.0")
    @DecimalMax(value = "30.0")
    @Column(name = "temperature")
    private float temperature;

    @NotNull
    @Column(name = "flips")
    private boolean flips;

    @NotNull
    @Column(name = "immunization")
    private boolean immunization;
}
