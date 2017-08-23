package com.dreamers.services;

import com.dreamers.entities.Aperture;
import com.dreamers.entities.Decoration;
import com.dreamers.entities.Wall;
import com.dreamers.repositories.WallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WallService {

    @Autowired
    private DecorationService decorationService;
    @Autowired
    private ApertureService apertureService;
    @Autowired
    private WallRepository wallRepository;

    public Wall save(Wall wall) {
        return wallRepository.save(wall);
    }

    public void delete(Long id) {
        decorationService.getDecorationsForWall(id)
                .forEach(decoration -> decorationService.delete(decoration.getId()));
        apertureService.getAperturesForWall(id)
                .forEach(aperture -> apertureService.delete(aperture.getId()));
        wallRepository.delete(id);
    }

    public List<Wall> getWallsForRoom(Long id) {
        return wallRepository.findByRoomId(id);
    }

}
