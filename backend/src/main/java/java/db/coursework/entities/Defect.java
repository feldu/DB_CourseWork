package java.db.coursework.entities;

import db.coursework.entities.Ovum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "defect")
@NoArgsConstructor
@Table(name = "defect")
public class Defect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ovum_id")
    private Ovum ovum;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "fixable")
    private boolean fixable;
}
