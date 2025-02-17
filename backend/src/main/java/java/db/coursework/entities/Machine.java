package java.db.coursework.entities;

import db.coursework.entities.Room;
import db.coursework.entities.enums.MachineName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "machine")
@NoArgsConstructor
@Table(name = "machine")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private MachineName name;
}
