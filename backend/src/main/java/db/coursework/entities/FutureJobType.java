package db.coursework.entities;

import db.coursework.entities.enums.FutureJobTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
@NoArgsConstructor
@Table(name = "future_job_type")
public class FutureJobType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private FutureJobTypeName name;
    @ManyToMany(mappedBy = "futureJobTypes", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();

    public FutureJobType(FutureJobTypeName name) {
        this.name = name;
    }
}
