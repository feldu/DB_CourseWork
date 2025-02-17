package java.db.coursework.repositories;

import db.coursework.entities.Machine;
import db.coursework.entities.enums.MachineName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MachineRepository extends JpaRepository<Machine, Long> {
    Optional<Machine> getFirstMachineByName(MachineName name);
}
