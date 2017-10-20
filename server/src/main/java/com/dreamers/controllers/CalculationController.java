package com.dreamers.controllers;

import com.dreamers.adapters.CalculationAdapter;
import com.dreamers.entities.Result;
import com.dreamers.services.CalculationResultService;
import com.dreamers.services.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(value = HttpStatus.OK)
    public void getCalculation(@RequestParam Long facilityId ) {
        calculationService.doCalculation(facilityId);
    }

    @GetMapping("/api/roomCalc")
    public List<Result> getCalculationForRooms(@RequestParam Long facilityId) {
        return calculationAdapter.getResultFromCalculation(resultService.findByFacilityId(facilityId));
    }

    @GetMapping("/api/facilityCalc")
    public List<Result> getCalculationForFacility(@RequestParam Long facilityId) {
        return calculationAdapter.getResultForFacilityFromCalculation(resultService.findByFacilityId(facilityId));
    }
}
