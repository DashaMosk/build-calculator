package com.dreamers.controllers;

import com.dreamers.entities.Wall;
import com.dreamers.services.WallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WallController {
    @Autowired
    private WallService wallService;

    @GetMapping("/api/{roomId}/walls")
    public List<Wall> getWalls(@PathVariable Long roomId) {
        return wallService.getWallsForRoom(roomId);
    }

    @PostMapping("/api/wall")
    public Wall saveWall(@RequestBody Wall wall) {
        return wallService.save(wall);
    }

    @DeleteMapping("/api/wall")
    public void deleteWall(@RequestParam Long id) {
        wallService.delete(id);
    }

    @GetMapping("/api/wall")
    public Wall getWall(@RequestParam Long id) {
        return wallService.getWall(id);
    }
}
