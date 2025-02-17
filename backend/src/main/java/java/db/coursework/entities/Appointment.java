package java.db.coursework.entities;

import db.coursework.entities.Human;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity(name = "appointment")
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "volunteer_id")
    private db.coursework.entities.Human volunteer;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "surgeon_id")
    private db.coursework.entities.Human surgeon;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id")
    private Human reviewer;

    @NotNull
    @Column(name = "operation_time")
    private Date operationTime;

    @NotNull
    @Column(name = "approved")
    private boolean approved;
}
