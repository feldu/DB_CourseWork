package db.coursework.repositories;

import db.coursework.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    @Query(value = "SELECT m.id, m.current_size, m.name, m.quality_parts_percentage, m.required_size FROM material m " +
            "WHERE NOT EXISTS (SELECT 1 FROM add_material_to_ovum_container m_oc where m_oc.material_id = m.id) " +
            "AND m.required_size = m.current_size " +
            "AND m.quality_parts_percentage = 100 " +
            "AND m.name = 'Свиной лоскут' " +
            "LIMIT 1",
            nativeQuery = true)
    Material getFreeMaterialForBottle();
}
