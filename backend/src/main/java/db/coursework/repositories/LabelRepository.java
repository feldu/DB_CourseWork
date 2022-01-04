package db.coursework.repositories;

import db.coursework.entities.Human;
import db.coursework.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findAllByAdditionalInformation_Human(Human human);
}
