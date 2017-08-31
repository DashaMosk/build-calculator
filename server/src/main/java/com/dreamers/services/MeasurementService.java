package com.dreamers.services;

import com.dreamers.entities.Measurement;
import com.dreamers.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;

    public void delete(Long id) {
        measurementRepository.delete(id);
    }

    public Measurement save(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    public List<Measurement> getAllMeasurement() {
        List<Measurement> list = new ArrayList<>();
        measurementRepository.findAll().iterator().forEachRemaining( list::add );
        return list;
    }

    public List<Measurement> getAllByFacilityId(Long facilityId) {
        List<Measurement> list = new ArrayList<>();
        measurementRepository.findByFacilityId(facilityId).iterator().forEachRemaining( list::add );
        return list;
    }

}
