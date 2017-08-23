package com.dreamers.services;

import com.dreamers.entities.Aperture;
import com.dreamers.entities.Room;
import com.dreamers.entities.Wall;
import com.dreamers.repositories.ApertureRepository;
import com.dreamers.repositories.DecorationRepository;
import com.dreamers.repositories.RoomRepository;
import com.dreamers.repositories.WallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private WallService wallService;
    @Autowired
    private RoomRepository roomRepository;

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        wallService.getWallsForRoom(id)
                .forEach(wall -> wallService.delete(wall.getId()));
        roomRepository.delete(id);
    }

    public Room getById(Long id) {
        return roomRepository.findOne(id);
    }

    public List<Room> getAllByFacilityId(Long id) {
        return roomRepository.findByFacilityId(id);
    }

}
