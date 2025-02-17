package java.db.coursework.entities;

import db.coursework.entities.Order;
import db.coursework.entities.OvumContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "label")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "additional_information_id")
    private Order additionalInformation;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ovum_container_id")
    private OvumContainer ovumContainer;

    @Min(value = 72)
    @Max(value = 96)
    @Column(name = "Bokanovsky_group")
    private Integer bokanovskyGroup;

    public Label(@NotNull Order additionalInformation, @NotNull OvumContainer ovumContainer, @Min(value = 72) @Max(value = 96) Integer bokanovskyGroup) {
        this.additionalInformation = additionalInformation;
        this.ovumContainer = ovumContainer;
        this.bokanovskyGroup = bokanovskyGroup;
    }
}
