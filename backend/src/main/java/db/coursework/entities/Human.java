package db.coursework.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "human")
@NoArgsConstructor
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Table(name = "human")
public class Human {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotEmpty
    @Column(name = "full_name", nullable = false)
    private String fullname;

    @JsonIgnoreProperties("humans")
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
