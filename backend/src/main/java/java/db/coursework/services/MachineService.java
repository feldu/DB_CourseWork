package java.db.coursework.services;

import db.coursework.entities.Machine;
import db.coursework.entities.enums.MachineName;
import db.coursework.exception.DataNotFoundException;
import db.coursework.repositories.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MachineService {
    private final MachineRepository machineRepository;

    @Autowired
    public MachineService(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public Machine getMachineByName(MachineName name) {
        return machineRepository.getFirstMachineByName(name).orElseThrow(() -> new DataNotFoundException("Machine not found"));
    }
}
