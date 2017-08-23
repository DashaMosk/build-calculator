package com.dreamers.services;

import com.dreamers.entities.Facility;
import com.dreamers.repositories.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService {

    @Autowired
    private RoomService roomService;
    @Autowired
    private FacilityRepository facilityRepository;

    public Facility save(Facility facility) {
        return facilityRepository.save(facility);
    }

    public List<Facility> getAllFacilities() {
        List<Facility> list = new ArrayList<>();
        facilityRepository.findAll().iterator().forEachRemaining( list::add );
        return list;
    }

    public void delete(Long id) {
        roomService.getAllByFacilityId(id)
                .forEach(room -> roomService.delete(room.getId()));
        facilityRepository.delete(id);
    }

    public Facility getById(Long id) {
        return facilityRepository.findOne(id);
    }
}
