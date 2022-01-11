package db.coursework.repositories;

import db.coursework.entities.Machine;
import db.coursework.entities.enums.MachineName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    Machine getMachineByName(MachineName name);
}
