package com.dreamers.services;

import com.dreamers.entities.Aperture;
import com.dreamers.repositories.ApertureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ApertureService {

    @Autowired
    private ApertureRepository apertureRepository;

    public Aperture save(Aperture aperture) {
        return apertureRepository.save(aperture);
    }

    public void delete(Long id) {
        apertureRepository.delete(id);
    }

    public List<Aperture> getAperturesForWall(Long id) {
        return apertureRepository.findByWallId(id);
    }

}
