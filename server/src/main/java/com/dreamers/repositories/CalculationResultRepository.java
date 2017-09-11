package com.dreamers.repositories;

import com.dreamers.entities.CalculationResult;
import com.dreamers.entities.Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculationResultRepository extends CrudRepository<CalculationResult, Long> {

    @Query("select c from CalculationResult c where c.measurement.wallId = :id")
    List<CalculationResult> findByWallId(@Param("id") Long id);

    @Query("select c from CalculationResult c where c.measurement.facilityId = :id")
    List<CalculationResult> findByFacilityId(@Param("id") Long id);

}
