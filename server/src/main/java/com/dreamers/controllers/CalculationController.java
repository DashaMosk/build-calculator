package com.dreamers.controllers;

import com.dreamers.adapters.CalculationAdapter;
import com.dreamers.entities.Result;
import com.dreamers.services.CalculationResultService;
import com.dreamers.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CalculationController {

    @Autowired
    private CalculationService calculationService;
    @Autowired
    private CalculationResultService resultService;
    @Autowired
    private CalculationAdapter calculationAdapter;

    @GetMapping("/api/calculation")
    public List<Result> getCalculation(@RequestParam Long facilityId, @RequestParam boolean recalculate ) {
        if(recalculate) {
            calculationService.doCalculation(facilityId);
        }
        return calculationAdapter.getResultFromCalculation(resultService.findByFacilityId(facilityId));
    }

    @GetMapping("/api/facilityCalc")
    public List<Result> getCalculationForFacility(@RequestParam Long facilityId) {
        return calculationAdapter.getResultForFacilityFromCalculation(resultService.findByFacilityId(facilityId));
    }
}
