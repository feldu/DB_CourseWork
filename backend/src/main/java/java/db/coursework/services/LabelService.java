package java.db.coursework.services;

import db.coursework.entities.Label;
import db.coursework.repositories.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelService {
    private final LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label save(Label label) {
        return labelRepository.save(label);
    }
}
