package com.dreamers.controllers;

import com.dreamers.entities.Aperture;
import com.dreamers.entities.Decoration;
import com.dreamers.services.ApertureService;
import com.dreamers.services.DecorationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ElementController {

    @Autowired
    private DecorationService decorationService;
    @Autowired
    private ApertureService apertureService;

    @GetMapping("/api/{wallId}/decorations")
    public List<Decoration> getDecorations(@PathVariable Long wallId) {
        return decorationService.getDecorationsForWall(wallId);
    }

    @PostMapping("/api/decoration")
    public Decoration saveDecoration(@RequestBody Decoration decoration) {
        return decorationService.save(decoration);
    }

    @DeleteMapping("/api/decoration")
    public void deleteDecoration(@RequestParam Long id) {
        decorationService.delete(id);
    }

    @GetMapping("/api/{wallId}/apertures")
    public List<Aperture> getApertures(@PathVariable Long wallId) {
        return apertureService.getAperturesForWall(wallId);
    }

    @PostMapping("/api/aperture")
    public Aperture saveAperture(@RequestBody Aperture aperture) {
        return apertureService.save(aperture);
    }

    @DeleteMapping("/api/aperture")
    public void deleteAperture(@RequestParam Long id) {
        apertureService.delete(id);
    }

}
