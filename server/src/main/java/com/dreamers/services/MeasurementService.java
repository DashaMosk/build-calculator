package com.dreamers.services;

import com.dreamers.entities.Measurement;
import com.dreamers.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
