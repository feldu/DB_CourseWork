package db.coursework.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "humanRoles")
@Entity(name = "human")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false)
    @Size(min = 1)
    private String fullname;

    @OneToMany(mappedBy = "human")
    private Set<HumanRole> humanRoles;

}
