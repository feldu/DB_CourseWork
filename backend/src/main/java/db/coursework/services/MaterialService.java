package db.coursework.services;

import db.coursework.entities.Material;
import db.coursework.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material getFreeMaterialForBottle() {
        return materialRepository.getFreeMaterialForBottle();
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }

    public void cutMaterial(Integer id, String name) {
        materialRepository.cutMaterial(id, name);
    }
}
