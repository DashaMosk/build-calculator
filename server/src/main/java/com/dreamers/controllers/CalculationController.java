package com.dreamers.controllers;

import com.dreamers.entities.Measurement;
import com.dreamers.services.CalculationService;
import com.dreamers.services.MeasurementService;
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
    private MeasurementService measurementService;

    @GetMapping("/api/calculation")
    public List<Measurement> getCalculation(@RequestParam Long facilityId, @RequestParam boolean recalculate ) {
        if(recalculate) {
            calculationService.doCalculation(facilityId);
        }
        return measurementService.getAllMeasurement();
    }


}
