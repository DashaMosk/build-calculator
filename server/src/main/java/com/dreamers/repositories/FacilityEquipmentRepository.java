package com.dreamers.repositories;

import com.dreamers.entities.FacilityEquipment;
import com.dreamers.entities.FacilityType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacilityEquipmentRepository extends CrudRepository<FacilityEquipment, Long> {

    @Query("select f from FacilityEquipment f where f.facilityID = :facilityID")
    List<FacilityEquipment> findByFacilityID(@Param("facilityID") Long facilityID);

    @Query("select f from FacilityEquipment f where f.facilityID = :id and f.fType = :type")
    List<FacilityEquipment> findByTypeAndFacilityID(@Param("type") FacilityType type, @Param("id") Long id);
}
