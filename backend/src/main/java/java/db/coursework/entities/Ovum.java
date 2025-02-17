package java.db.coursework.entities;

import db.coursework.entities.Human;
import db.coursework.entities.Order;
import db.coursework.entities.OvumContainer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "ovum")
@NoArgsConstructor
@Table(name = "ovum")
public class Ovum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;


    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volunteer_id")
    private Human volunteer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ovum_container_id")
    private OvumContainer ovumContainer;

    @NotNull
    @Column(name = "is_bud")
    private boolean isBud;

    @Column(name = "fertilization_time")
    private Date fertilizationTime;

    @Column(name = "embryo_time")
    private Date embryoTime;

    @Column(name = "baby_time")
    private Date babyTime;
}
