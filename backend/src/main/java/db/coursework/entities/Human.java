package db.coursework.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Entity(name = "human")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false)
    @Size(min = 1)
    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "human_role",
            joinColumns = {
                    @JoinColumn(name = "human_id", referencedColumnName = "id",
                            nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id",
                            nullable = false)})
    private Set<Role> roles = new HashSet<>();

    public Human(@Size(min = 1) String fullname, Set<Role> roles) {
        this.fullname = fullname;
        this.roles = roles;
    }
}
