package com.dreamers.services;

import com.dreamers.entities.Stuff;
import com.dreamers.repositories.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StuffService {
    @Autowired
    private StuffRepository stuffRepository;

    public Stuff save(Stuff stuff) {
        return stuffRepository.save(stuff);
    }

    public void delete(Long id) {
        stuffRepository.delete(id);
    }

    public List<Stuff> findByCleanParam(boolean clean) {
        return stuffRepository.findByCleanParam(clean);
    }

    public List<Stuff> findAll() {
        List<Stuff> stuffList = new ArrayList<>();
        stuffRepository.findAll().forEach(stuffList::add);
        return stuffList;
    }
}
