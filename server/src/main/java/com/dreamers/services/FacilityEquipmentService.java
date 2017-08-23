package com.dreamers.services;

import com.dreamers.entities.FacilityEquipment;
import com.dreamers.repositories.FacilityEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityEquipmentService {

    @Autowired
    private FacilityEquipmentRepository facilityEquipmentRepository;

    public FacilityEquipment save(FacilityEquipment equipment) {
        return facilityEquipmentRepository.save(equipment);
    }

    public void delete(Long id) {
        facilityEquipmentRepository.delete(id);
    }

    public List<FacilityEquipment> findByFacilityID(Long id) {
        return facilityEquipmentRepository.findByFacilityID(id);
    }
}
