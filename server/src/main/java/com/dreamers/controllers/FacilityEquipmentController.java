package com.dreamers.controllers;

import com.dreamers.entities.FacilityEquipment;
import com.dreamers.entities.FacilityType;
import com.dreamers.repositories.FacilityEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FacilityEquipmentController {
    @Autowired
    private FacilityEquipmentRepository equipmentRepository;

    @PostMapping("/api/equipment")
    public FacilityEquipment save(@RequestBody FacilityEquipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @DeleteMapping("/api/equipment")
    public void delete(@RequestParam Long id) {
        equipmentRepository.delete(id);
    }

    @GetMapping("/api/equipmentList")
    public List<FacilityEquipment> findByTypeAndFacilityId(@RequestParam Long id, @RequestParam FacilityType type) {
        return equipmentRepository.findByTypeAndFacilityID(type, id);
    }
}
