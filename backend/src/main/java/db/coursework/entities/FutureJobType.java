package db.coursework.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
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
    //todo: add Enum
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "futureJobTypes", fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<>();
}
