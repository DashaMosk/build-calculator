package com.dreamers.controllers;

import com.dreamers.entities.Room;
import com.dreamers.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/api/{facilityId}/rooms")
    public List<Room> getAllByFacilityId(@PathVariable Long facilityId) {
        return roomService.getAllByFacilityId(facilityId);
    }

    @PostMapping(value = "/api/room")
    public Room saveRoom(@RequestBody Room room) {
        return roomService.save(room);
    }

    @GetMapping("/api/room")
    public Room getRoomById(@RequestParam Long id) {
        return roomService.getById(id);
    }

    @DeleteMapping("/api/room")
    public void deleteRoom(@RequestParam Long id) {
        roomService.delete(id);
    }

}
