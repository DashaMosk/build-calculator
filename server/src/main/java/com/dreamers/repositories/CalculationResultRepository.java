package com.dreamers.repositories;

import com.dreamers.entities.CalculationResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationResultRepository extends CrudRepository<CalculationResult, Long> {
}
