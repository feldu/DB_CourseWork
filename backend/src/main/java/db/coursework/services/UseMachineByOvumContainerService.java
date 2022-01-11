package db.coursework.services;

import db.coursework.entities.UseMachineByOvumContainer;
import db.coursework.repositories.UseMachineByOvumContainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseMachineByOvumContainerService {

    private final UseMachineByOvumContainerRepository useMachineByOvumContainerRepository;

    @Autowired
    public UseMachineByOvumContainerService(UseMachineByOvumContainerRepository useMachineByOvumContainerRepository) {
        this.useMachineByOvumContainerRepository = useMachineByOvumContainerRepository;
    }

    public UseMachineByOvumContainer save(UseMachineByOvumContainer useMachineByOvumContainer) {
        return useMachineByOvumContainerRepository.save(useMachineByOvumContainer);
    }
}
