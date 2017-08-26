package com.dreamers.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CalculationController {

    @GetMapping("/api/calculation")
    public void getCalculation(@RequestParam Long facilityId, @RequestParam boolean recalculate ) {

    }


}
