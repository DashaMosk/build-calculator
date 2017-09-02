package com.dreamers.services;

import com.dreamers.entities.FacilityEquipment;
import com.dreamers.entities.FacilityType;
import com.dreamers.entities.PartType;
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

    public List<FacilityEquipment> findByTypeAndFacilityID(FacilityType type, Long id) {
        return facilityEquipmentRepository.findByTypeAndFacilityID(type, id);
    }

    public List<FacilityEquipment> findByTypeFacilityIDClean(FacilityType type, Long id, boolean clean) {
        return facilityEquipmentRepository.findByTypeFacilityIDClean(type, id, clean);
    }

    public List<FacilityEquipment> findByTypeFacilityIdPartTypeAndClean(FacilityType type, Long id, boolean clean, PartType pType) {
        return facilityEquipmentRepository.findByTypeFacilityIdPartTypeAndClean(type, id, clean, pType);
    }
}
