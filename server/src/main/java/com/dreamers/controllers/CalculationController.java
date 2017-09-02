package com.dreamers.controllers;

import com.dreamers.entities.CalculationResult;
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

    @GetMapping("/api/calculation")
    public List<CalculationResult> getCalculation(@RequestParam Long facilityId, @RequestParam boolean recalculate ) {
        if(recalculate) {
            calculationService.doCalculation(facilityId);
        }
        return resultService.findByWallId(facilityId);
    }
}
