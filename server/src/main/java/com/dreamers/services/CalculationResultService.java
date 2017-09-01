package com.dreamers.services;

import com.dreamers.entities.CalculationResult;
import com.dreamers.repositories.CalculationResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationResultService {

    @Autowired
    private CalculationResultRepository calculationResultRepository;

    public CalculationResult save(CalculationResult calculationResult) {
        return calculationResultRepository.save(calculationResult);
    }

    public void delete(Long id) {
        calculationResultRepository.delete(id);
    }

    public List<CalculationResult> findByWallId(Long wallId) {
        return calculationResultRepository.findByWallId(wallId);
    }

}
