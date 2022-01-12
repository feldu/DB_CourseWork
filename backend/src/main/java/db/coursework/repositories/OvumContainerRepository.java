package db.coursework.repositories;

import db.coursework.entities.OvumContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OvumContainerRepository extends JpaRepository<OvumContainer, Long> {
    @Query(value = "SELECT oc.id, oc.name FROM ovum_container oc " +
            "WHERE NOT EXISTS (SELECT 1 FROM ovum o where o.ovum_container_id = oc.id) " +
            "AND oc.name = 'OVUMRECEIVER' LIMIT 1",
            nativeQuery = true)
    OvumContainer getFreeOvumreceiver();

    @Query(value = "select oc from ovum_container oc " +
            "join ovum o on (o.ovumContainer = oc) " +
            "WHERE o.order.id = :orderId " +
            "AND oc.name = 'OVUMRECEIVER'")
    OvumContainer getOrderOvumreceiver(@Param(value = "orderId") Long orderId);

    @Query(value = "SELECT oc.id, oc.name FROM ovum_container oc " +
            "WHERE NOT EXISTS (SELECT 1 FROM ovum o where o.ovum_container_id = oc.id) " +
            "AND oc.name = 'BOTTLE' LIMIT :count",
            nativeQuery = true)
    List<OvumContainer> getFreeBottles(@Param(value = "count") Integer count);
}
