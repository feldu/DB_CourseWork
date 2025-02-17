package java.db.coursework.services;

import db.coursework.entities.AddMaterialToOvumContainer;
import db.coursework.repositories.AddMaterialToOvumContainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddMaterialToOvumContainerService {
    private final AddMaterialToOvumContainerRepository addMaterialToOvumContainerRepository;

    public AddMaterialToOvumContainerService(AddMaterialToOvumContainerRepository addMaterialToOvumContainerRepository) {
        this.addMaterialToOvumContainerRepository = addMaterialToOvumContainerRepository;
    }

    public AddMaterialToOvumContainer save(AddMaterialToOvumContainer addMaterialToOvumContainer) {
        return addMaterialToOvumContainerRepository.save(addMaterialToOvumContainer);
    }

    public List<AddMaterialToOvumContainer> findAllByOvumContainer_Id(Long id) {
        return addMaterialToOvumContainerRepository.findAllByOvumContainer_Id(id);
    }

}
