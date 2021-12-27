package db.coursework.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class HumanRoleKey implements Serializable {
    @Column(name = "human_id")
    long humanId;
    @Column(name = "role_id")
    long roleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanRoleKey that = (HumanRoleKey) o;
        return humanId == that.humanId &&
                roleId == that.roleId;
    }
}
