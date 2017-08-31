package com.dreamers.repositories;

import com.dreamers.entities.Measurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {

    @Query("select m from Measurement m where m.facilityId = :id")
    List<Measurement> findByFacilityId(@Param("id") Long id);
}
