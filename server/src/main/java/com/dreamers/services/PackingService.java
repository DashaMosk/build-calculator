package com.dreamers.services;

import com.dreamers.entities.Packing;
import com.dreamers.repositories.PackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PackingService {
    @Autowired
    private PackingRepository packingRepository;

    public Packing save(Packing pack) {
        return packingRepository.save(pack);
    }

    public void delete(Long id) {
        packingRepository.delete(id);
    }

    public List<Packing> findAll() {
        List<Packing> list = new ArrayList<>();
        packingRepository.findAll().iterator().forEachRemaining( list::add );
        return list;
    }

    public Packing findById(Long id) {
        return packingRepository.findOne(id);
    }
}
