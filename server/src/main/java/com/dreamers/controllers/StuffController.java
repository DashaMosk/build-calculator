package com.dreamers.controllers;

import com.dreamers.entities.Stuff;
import com.dreamers.services.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StuffController {

    @Autowired
    private StuffService stuffService;

    @PostMapping("/api/stuff")
    public Stuff save(@RequestBody Stuff stuff) {
        return stuffService.save(stuff);
    }

    @DeleteMapping("/api/stuff")
    public void delete(@RequestParam Long id) {
        stuffService.delete(id);
    }

    @GetMapping("/api/stuffByClean")
    public List<Stuff> findByCleanParam(@RequestParam boolean clean) {
        return stuffService.findByCleanParam(clean);
    }

    @GetMapping("/api/allStuff")
    public List<Stuff> findAllStuff() {
        return stuffService.findAll();
    }
}
