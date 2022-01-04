package db.coursework.entities;

import db.coursework.entities.enums.OvumContainerName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity(name = "ovum_container")
public class OvumContainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private OvumContainerName name;
}


