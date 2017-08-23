package com.dreamers.services;

import com.dreamers.entities.Decoration;
import com.dreamers.repositories.DecorationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecorationService {
    @Autowired
    private DecorationRepository decorationRepository;

    public Decoration save(Decoration decoration) {
        return decorationRepository.save(decoration);
    }

    public void delete(Long id) {
        decorationRepository.delete(id);
    }

    public List<Decoration> getDecorationsForWall(Long id) {
        return decorationRepository.findByWallId(id);
    }
}
