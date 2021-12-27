package db.coursework.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class HumanRole {
    @EmbeddedId
    HumanRoleKey id;
    @ManyToOne
    @MapsId("humanId")
    @JoinColumn(name = "human_id")
    Human human;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    Role role;

    public HumanRole(Human human, Role role) {
        this.human = human;
        this.role = role;
    }
}
