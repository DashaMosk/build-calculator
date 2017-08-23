package com.dreamers.controllers;

import com.dreamers.entities.Facility;
import com.dreamers.services.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @GetMapping("/api/facilities")
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @PostMapping("/api/facility")
    public Facility save(@RequestBody Facility facility) {
        return facilityService.save(facility);
    }

    @DeleteMapping("/api/facility")
    public void delete(@RequestParam Long id) {
        facilityService.delete(id);
    }

    @GetMapping("/api/facility")
    public Facility getById(@RequestParam Long id) {
        return facilityService.getById(id);
    }

}
