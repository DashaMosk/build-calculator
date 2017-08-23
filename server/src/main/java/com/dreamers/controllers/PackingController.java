package com.dreamers.controllers;

import com.dreamers.entities.Packing;
import com.dreamers.services.PackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PackingController {

    @Autowired
    private PackingService packingService;

    @PostMapping("/api/packing")
    public Packing save(@RequestBody Packing packing) {
        return packingService.save(packing);
    }

    @DeleteMapping("/api/packing")
    public void delete(@RequestParam Long id) {
        packingService.delete(id);
    }

    @GetMapping("/api/packingList")
    public List<Packing> findAll() {
        return packingService.findAll();
    }

    @GetMapping("/api/packing")
    public Packing findById(@RequestParam Long id) {
        return packingService.findById(id);
    }
}
